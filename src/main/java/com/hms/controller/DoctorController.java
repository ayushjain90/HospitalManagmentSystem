package com.hms.controller;

import com.hms.entity.Doctor;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/doctor")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<Doctor> doctorOpt = doctorService.findByUsername(username);

        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            model.addAttribute("doctor", doctor);
            model.addAttribute("upcomingAppointments", appointmentService.getUpcomingAppointmentsByDoctor(doctor));
            model.addAttribute("todayAppointments", appointmentService.getTodayAppointmentCount());
        }

        return "doctor/dashboard";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<Doctor> doctorOpt = doctorService.findByUsername(username);

        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            model.addAttribute("appointments", appointmentService.findByDoctor(doctor));
        }

        return "doctor/appointments";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<Doctor> doctorOpt = doctorService.findByUsername(username);

        if (doctorOpt.isPresent()) {
            model.addAttribute("doctor", doctorOpt.get());
        }

        return "doctor/profile";
    }

    @PostMapping("/appointments/{id}/confirm")
    public String confirmAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.confirmAppointment(id);
            redirectAttributes.addFlashAttribute("message", "Appointment confirmed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to confirm appointment.");
        }
        return "redirect:/doctor/appointments";
    }

    @PostMapping("/appointments/{id}/complete")
    public String completeAppointment(@PathVariable Long id,
            @RequestParam String notes,
            @RequestParam String prescription,
            RedirectAttributes redirectAttributes) {
        try {
            appointmentService.completeAppointment(id, notes, prescription);
            redirectAttributes.addFlashAttribute("message", "Appointment completed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to complete appointment.");
        }
        return "redirect:/doctor/appointments";
    }

    @PostMapping("/appointments/{id}/reject")
    public String rejectAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.rejectAppointment(id);
            redirectAttributes.addFlashAttribute("message", "Appointment rejected successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to reject appointment.");
        }
        return "redirect:/doctor/appointments";
    }

    // REST API endpoints for AJAX calls
    @PostMapping("/api/appointments/{id}/accept")
    @ResponseBody
    public ResponseEntity<String> acceptAppointment(@PathVariable Long id) {
        try {
            appointmentService.confirmAppointment(id);
            return ResponseEntity.ok("Appointment accepted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to accept appointment: " + e.getMessage());
        }
    }

    @PostMapping("/api/appointments/{id}/reject")
    @ResponseBody
    public ResponseEntity<String> rejectAppointmentApi(@PathVariable Long id) {
        try {
            appointmentService.rejectAppointment(id);
            return ResponseEntity.ok("Appointment rejected successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to reject appointment: " + e.getMessage());
        }
    }

    @PostMapping("/api/appointments/{id}/complete")
    @ResponseBody
    public ResponseEntity<String> completeAppointmentApi(@PathVariable Long id,
            @RequestParam String notes,
            @RequestParam String prescription) {
        try {
            appointmentService.completeAppointment(id, notes, prescription);
            return ResponseEntity.ok("Appointment completed successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to complete appointment: " + e.getMessage());
        }
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Doctor doctorUpdate,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            Optional<Doctor> doctorOpt = doctorService.findByUsername(username);

            if (doctorOpt.isPresent()) {
                Doctor existingDoctor = doctorOpt.get();

                // Update allowed fields (excluding salary-related fields like consultationFee)
                existingDoctor.setSpecialization(doctorUpdate.getSpecialization());
                existingDoctor.setExperienceYears(doctorUpdate.getExperienceYears());
                existingDoctor.setQualifications(doctorUpdate.getQualifications());
                existingDoctor.setWorkingHours(doctorUpdate.getWorkingHours());

                // Update User fields
                existingDoctor.setFullName(doctorUpdate.getFullName());
                existingDoctor.setEmail(doctorUpdate.getEmail());
                existingDoctor.setPhoneNumber(doctorUpdate.getPhoneNumber());

                doctorService.updateDoctor(existingDoctor);
                redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Doctor not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update profile: " + e.getMessage());
        }

        return "redirect:/doctor/profile";
    }

    @PostMapping("/toggle-availability")
    public String toggleAvailability(Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Optional<Doctor> doctorOpt = doctorService.findByUsername(username);

        if (doctorOpt.isPresent()) {
            doctorService.toggleAvailability(doctorOpt.get().getId());
            redirectAttributes.addFlashAttribute("message", "Availability status updated!");
        }

        return "redirect:/doctor/dashboard";
    }
}