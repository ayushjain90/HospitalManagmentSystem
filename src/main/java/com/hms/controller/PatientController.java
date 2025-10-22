package com.hms.controller;

import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/patient")
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<Patient> patientOpt = patientService.findByUsername(username);

        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            model.addAttribute("patient", patient);
            model.addAttribute("upcomingAppointments", appointmentService.getUpcomingAppointmentsByPatient(patient));
        }

        return "patient/dashboard";
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<Patient> patientOpt = patientService.findByUsername(username);

        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            model.addAttribute("appointments", appointmentService.findByPatient(patient));
        }

        return "patient/appointments";
    }

    @GetMapping("/book-appointment")
    public String showBookAppointmentForm(Model model) {
        model.addAttribute("doctors", doctorService.getAvailableDoctors());
        model.addAttribute("specializations", doctorService.getAllSpecializations());
        return "patient/book-appointment";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(@RequestParam Long doctorId,
            @RequestParam String appointmentDateTime,
            @RequestParam String reason,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            Optional<Patient> patientOpt = patientService.findByUsername(username);
            Optional<Doctor> doctorOpt = doctorService.findById(doctorId);

            if (patientOpt.isPresent() && doctorOpt.isPresent()) {
                Patient patient = patientOpt.get();
                Doctor doctor = doctorOpt.get();
                LocalDateTime dateTime = LocalDateTime.parse(appointmentDateTime);

                if (appointmentService.isSlotAvailable(doctor, dateTime)) {
                    appointmentService.scheduleAppointment(patient, doctor, dateTime, reason);
                    redirectAttributes.addFlashAttribute("message", "Appointment booked successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("error",
                            "Selected time slot is not available. Please choose another time.");
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to book appointment. Please try again.");
        }

        return "redirect:/patient/appointments";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<Patient> patientOpt = patientService.findByUsername(username);

        if (patientOpt.isPresent()) {
            model.addAttribute("patient", patientOpt.get());
        }

        return "patient/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Patient patientUpdate,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            String username = authentication.getName();
            Optional<Patient> existingPatientOpt = patientService.findByUsername(username);

            if (existingPatientOpt.isPresent()) {
                Patient existingPatient = existingPatientOpt.get();

                // Update allowed fields (not username or sensitive data)
                existingPatient.setFullName(patientUpdate.getFullName());
                existingPatient.setEmail(patientUpdate.getEmail());
                existingPatient.setPhoneNumber(patientUpdate.getPhoneNumber());
                existingPatient.setDateOfBirth(patientUpdate.getDateOfBirth());
                existingPatient.setGender(patientUpdate.getGender());
                existingPatient.setAddress(patientUpdate.getAddress());
                existingPatient.setEmergencyContact(patientUpdate.getEmergencyContact());
                existingPatient.setMedicalHistory(patientUpdate.getMedicalHistory());
                existingPatient.setBloodGroup(patientUpdate.getBloodGroup());

                patientService.savePatient(existingPatient);
                redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Patient not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update profile: " + e.getMessage());
        }

        return "redirect:/patient/profile";
    }

    @PostMapping("/appointments/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.cancelAppointment(id);
            redirectAttributes.addFlashAttribute("message", "Appointment cancelled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel appointment.");
        }
        return "redirect:/patient/appointments";
    }

    @GetMapping("/doctors")
    public String viewDoctors(Model model, @RequestParam(required = false) String specialization) {
        java.util.List<Doctor> allDoctors;
        if (specialization != null && !specialization.isEmpty()) {
            allDoctors = doctorService.findAvailableDoctorsBySpecialization(specialization);
        } else {
            // Get all doctors regardless of availability to show complete list
            allDoctors = doctorService.getAllDoctors();
        }

        System.out.println("DEBUG: Found " + (allDoctors != null ? allDoctors.size() : "null") + " doctors");
        if (allDoctors != null && !allDoctors.isEmpty()) {
            allDoctors.forEach(d -> System.out
                    .println("Doctor: " + d.getFullName() + " - Available: " + d.isAvailableForConsultation()));
        } else {
            System.out.println("DEBUG: No doctors found in database!");
        }

        model.addAttribute("doctors", allDoctors);
        model.addAttribute("specializations", doctorService.getAllSpecializations());
        model.addAttribute("selectedSpecialization", specialization);
        return "patient/doctors";
    }

    @GetMapping("/api/doctor/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getDoctorDetails(@PathVariable Long id) {
        try {
            Optional<Doctor> doctorOpt = doctorService.findById(id);

            if (doctorOpt.isPresent()) {
                Doctor doctor = doctorOpt.get();
                Map<String, Object> response = new HashMap<>();

                response.put("id", doctor.getId());
                response.put("fullName", doctor.getFullName());
                response.put("email", doctor.getEmail());
                response.put("phoneNumber", doctor.getPhoneNumber());
                response.put("specialization", doctor.getSpecialization());
                response.put("experienceYears", doctor.getExperienceYears());
                response.put("qualifications", doctor.getQualifications() != null ? doctor.getQualifications()
                        : "Qualified Medical Professional");
                response.put("consultationFee",
                        doctor.getConsultationFee() != null ? doctor.getConsultationFee() : "Contact for fee");
                response.put("workingHours",
                        doctor.getWorkingHours() != null ? doctor.getWorkingHours() : "Contact for schedule");
                response.put("availableForConsultation", doctor.isAvailableForConsultation());
                response.put("licenseNumber",
                        doctor.getLicenseNumber() != null ? doctor.getLicenseNumber() : "Licensed Practitioner");

                // Calculate rating (mock data for now - you can implement actual rating system
                // later)
                response.put("rating", 4.5 + (doctor.getId() % 5) * 0.1); // Mock rating between 4.5-4.9
                response.put("totalReviews", 50 + (doctor.getId() % 100)); // Mock review count

                // Add additional information
                response.put("totalPatients", 100 + (doctor.getExperienceYears() * 15)); // Mock patient count
                response.put("successRate", 85 + (doctor.getId() % 15)); // Mock success rate 85-99%

                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Doctor not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to fetch doctor details: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/api/doctors/all")
    @ResponseBody
    public ResponseEntity<java.util.List<Map<String, Object>>> getAllDoctorsForModal() {
        try {
            java.util.List<Doctor> doctors = doctorService.getAllDoctors();
            java.util.List<Map<String, Object>> response = new java.util.ArrayList<>();

            for (Doctor doctor : doctors) {
                Map<String, Object> doctorData = new HashMap<>();

                doctorData.put("id", doctor.getId());
                doctorData.put("fullName", doctor.getFullName());
                doctorData.put("email", doctor.getEmail());
                doctorData.put("phoneNumber", doctor.getPhoneNumber());
                doctorData.put("specialization", doctor.getSpecialization());
                doctorData.put("experienceYears", doctor.getExperienceYears());
                doctorData.put("qualifications", doctor.getQualifications() != null ? doctor.getQualifications()
                        : "Qualified Medical Professional");
                doctorData.put("consultationFee",
                        doctor.getConsultationFee() != null ? doctor.getConsultationFee() : "Contact for fee");
                doctorData.put("workingHours",
                        doctor.getWorkingHours() != null ? doctor.getWorkingHours() : "Contact for schedule");
                doctorData.put("availableForConsultation", doctor.isAvailableForConsultation());
                doctorData.put("licenseNumber",
                        doctor.getLicenseNumber() != null ? doctor.getLicenseNumber() : "Licensed Practitioner");

                // Calculate rating (mock data for now - you can implement actual rating system
                // later)
                doctorData.put("rating", 4.5 + (doctor.getId() % 5) * 0.1); // Mock rating between 4.5-4.9
                doctorData.put("totalReviews", 50 + (doctor.getId() % 100)); // Mock review count

                // Add additional information
                doctorData.put("totalPatients", 100 + (doctor.getExperienceYears() * 15)); // Mock patient count
                doctorData.put("successRate", 85 + (doctor.getId() % 15)); // Mock success rate 85-99%

                response.add(doctorData);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new java.util.ArrayList<>());
        }
    }
}