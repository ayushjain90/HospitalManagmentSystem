package com.hms.controller;

import com.hms.entity.Appointment;
import com.hms.entity.Bill;
import com.hms.entity.Patient;
import com.hms.service.AppointmentService;
import com.hms.service.BillService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    // Admin - View all bills
    @GetMapping("/admin")
    public String adminBillsPage(Model model) {
        List<Bill> bills = billService.getAllBills();
        BillService.BillStatistics statistics = billService.getBillStatistics();

        model.addAttribute("bills", bills);
        model.addAttribute("statistics", statistics);
        return "admin/bills";
    }

    // Admin - Create new bill
    @GetMapping("/admin/create")
    public String createBillPage(Model model, @RequestParam(required = false) Long appointmentId) {
        model.addAttribute("bill", new Bill());

        if (appointmentId != null) {
            Optional<Appointment> appointment = appointmentService.findById(appointmentId);
            if (appointment.isPresent() && !billService.hasAppointmentBill(appointmentId)) {
                model.addAttribute("appointment", appointment.get());
            }
        }

        List<Appointment> completedAppointments = appointmentService.getCompletedAppointments();
        // Filter out appointments that already have bills
        completedAppointments.removeIf(apt -> billService.hasAppointmentBill(apt.getId()));
        model.addAttribute("availableAppointments", completedAppointments);

        return "admin/create-bill";
    }

    // Admin - Save new bill
    @PostMapping("/admin/create")
    public String createBill(@RequestParam Long appointmentId,
            @RequestParam BigDecimal consultationFee,
            @RequestParam(defaultValue = "0") BigDecimal medicineCost,
            @RequestParam(defaultValue = "0") BigDecimal testCost,
            @RequestParam(defaultValue = "0") BigDecimal roomCharges,
            @RequestParam(defaultValue = "0") BigDecimal otherCharges,
            @RequestParam(defaultValue = "0") BigDecimal discount,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Appointment> appointmentOpt = appointmentService.findById(appointmentId);
            if (!appointmentOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Appointment not found");
                return "redirect:/bills/admin/create";
            }

            Appointment appointment = appointmentOpt.get();
            Bill bill = billService.createBill(appointment, appointment.getPatient(),
                    consultationFee, medicineCost, testCost,
                    roomCharges, otherCharges, discount, notes);

            redirectAttributes.addFlashAttribute("success", "Bill created successfully");
            return "redirect:/bills/admin/view/" + bill.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating bill: " + e.getMessage());
            return "redirect:/bills/admin/create";
        }
    }

    // Admin - Edit bill
    @GetMapping("/admin/edit/{id}")
    public String editBillPage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Bill> billOpt = billService.getBillById(id);
        if (!billOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Bill not found");
            return "redirect:/bills/admin";
        }

        model.addAttribute("bill", billOpt.get());
        return "admin/edit-bill";
    }

    // Admin - Update bill
    @PostMapping("/admin/edit/{id}")
    public String updateBill(@PathVariable Long id,
            @RequestParam BigDecimal consultationFee,
            @RequestParam(defaultValue = "0") BigDecimal medicineCost,
            @RequestParam(defaultValue = "0") BigDecimal testCost,
            @RequestParam(defaultValue = "0") BigDecimal roomCharges,
            @RequestParam(defaultValue = "0") BigDecimal otherCharges,
            @RequestParam(defaultValue = "0") BigDecimal discount,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {
        try {
            Bill bill = billService.updateBillCharges(id, consultationFee, medicineCost,
                    testCost, roomCharges, otherCharges,
                    discount, notes);
            redirectAttributes.addFlashAttribute("success", "Bill updated successfully");
            return "redirect:/bills/admin/view/" + bill.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating bill: " + e.getMessage());
            return "redirect:/bills/admin/edit/" + id;
        }
    }

    // Admin - View bill details
    @GetMapping("/admin/view/{id}")
    public String viewBillDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Bill> billOpt = billService.getBillById(id);
        if (!billOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Bill not found");
            return "redirect:/bills/admin";
        }

        model.addAttribute("bill", billOpt.get());
        return "admin/view-bill";
    }

    // Admin - Mark bill as paid
    @PostMapping("/admin/mark-paid/{id}")
    public String markBillAsPaid(@PathVariable Long id,
            @RequestParam String paymentMethod,
            RedirectAttributes redirectAttributes) {
        try {
            billService.markAsPaid(id, paymentMethod);
            redirectAttributes.addFlashAttribute("success", "Bill marked as paid successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating payment status: " + e.getMessage());
        }
        return "redirect:/bills/admin/view/" + id;
    }

    // Patient - View their bills
    @GetMapping("/patient")
    public String patientBillsPage(Model model, Authentication authentication) {
        String username = authentication.getName();

        Optional<Patient> patientOpt = patientService.findByUsername(username);
        if (!patientOpt.isPresent()) {
            model.addAttribute("error", "Patient profile not found");
            return "patient/bills";
        }

        Patient patient = patientOpt.get();
        List<Bill> bills = billService.getBillsByPatient(patient);
        Double totalUnpaid = billService.getTotalUnpaidAmountByPatientId(patient.getId());

        model.addAttribute("bills", bills);
        model.addAttribute("patient", patient);
        model.addAttribute("totalUnpaid", totalUnpaid);
        return "patient/bills";
    }

    // Patient - View bill details
    @GetMapping("/patient/view/{id}")
    public String patientViewBill(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes,
            Authentication authentication) {
        String username = authentication.getName();

        Optional<Patient> patientOpt = patientService.findByUsername(username);
        if (!patientOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Patient profile not found");
            return "redirect:/bills/patient";
        }

        Optional<Bill> billOpt = billService.getBillById(id);
        if (!billOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Bill not found");
            return "redirect:/bills/patient";
        }

        Bill bill = billOpt.get();
        // Ensure patient can only view their own bills
        if (!bill.getPatient().getId().equals(patientOpt.get().getId())) {
            redirectAttributes.addFlashAttribute("error", "Access denied");
            return "redirect:/bills/patient";
        }

        model.addAttribute("bill", bill);
        return "patient/view-bill";
    }

    // Patient - Print bill (PDF view)
    @GetMapping("/patient/print/{id}")
    public String printBill(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes,
            Authentication authentication) {
        String username = authentication.getName();

        Optional<Patient> patientOpt = patientService.findByUsername(username);
        if (!patientOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Patient profile not found");
            return "redirect:/bills/patient";
        }

        Optional<Bill> billOpt = billService.getBillById(id);
        if (!billOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Bill not found");
            return "redirect:/bills/patient";
        }

        Bill bill = billOpt.get();
        // Ensure patient can only print their own bills
        if (!bill.getPatient().getId().equals(patientOpt.get().getId())) {
            redirectAttributes.addFlashAttribute("error", "Access denied");
            return "redirect:/bills/patient";
        }

        model.addAttribute("bill", bill);
        return "patient/print-bill";
    }

    // Generate bill after appointment completion
    @PostMapping("/generate/{appointmentId}")
    public String generateBill(@PathVariable Long appointmentId,
            @RequestParam BigDecimal consultationFee,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Appointment> appointmentOpt = appointmentService.findById(appointmentId);
            if (!appointmentOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Appointment not found");
                return "redirect:/admin/appointments";
            }

            Bill bill = billService.generateBillForAppointment(appointmentOpt.get(), consultationFee);
            redirectAttributes.addFlashAttribute("success", "Bill generated successfully");
            return "redirect:/bills/admin/view/" + bill.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error generating bill: " + e.getMessage());
            return "redirect:/admin/appointments";
        }
    }
}