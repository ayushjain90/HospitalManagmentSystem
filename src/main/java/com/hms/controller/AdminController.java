package com.hms.controller;

import com.hms.dto.AppointmentDto;
import com.hms.dto.AppointmentDetailDto;
import com.hms.entity.Appointment;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalPatients", patientService.getActivePatientCount());
        model.addAttribute("totalDoctors", doctorService.getActiveDoctorCount());
        model.addAttribute("totalAppointments", appointmentService.getScheduledAppointmentCount());
        model.addAttribute("todayAppointments", appointmentService.getTodayAppointmentCount());
        model.addAttribute("recentAppointments", appointmentService.getUpcomingAppointments());
        return "admin/dashboard";
    }

    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getActivePatients());
        model.addAttribute("totalPatients", patientService.getActivePatientCount());
        model.addAttribute("activePatients", patientService.getActivePatientCount());
        model.addAttribute("criticalPatients", 0); // Placeholder for critical patients
        model.addAttribute("newPatientsThisMonth", patientService.getNewPatientsThisMonth());
        return "admin/patients";
    }

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getActiveDoctors());
        model.addAttribute("totalDoctors", doctorService.getActiveDoctorCount());
        model.addAttribute("availableDoctors", doctorService.getAvailableDoctorCount());
        model.addAttribute("busyDoctors", doctorService.getBusyDoctorCount());
        model.addAttribute("newDoctorsThisMonth", doctorService.getNewDoctorsThisMonth());
        return "admin/doctors";
    }

    @GetMapping("/appointments")
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("patients", patientService.getActivePatients());
        model.addAttribute("doctors", doctorService.getActiveDoctors());
        model.addAttribute("totalAppointments", appointmentService.getScheduledAppointmentCount());
        model.addAttribute("todayAppointments", appointmentService.getTodayAppointmentCount());
        model.addAttribute("pendingConfirmations", appointmentService.getPendingConfirmationCount());
        model.addAttribute("completedToday", appointmentService.getCompletedTodayCount());
        return "admin/appointments";
    }

    @GetMapping("/doctors/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin/add-doctor";
    }

    @PostMapping("/doctors/add")
    public String addDoctor(@ModelAttribute Doctor doctor, RedirectAttributes redirectAttributes) {
        try {
            doctorService.registerNewDoctor(doctor);
            redirectAttributes.addFlashAttribute("message", "Doctor added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add doctor. Please try again.");
        }
        return "redirect:/admin/doctors";
    }

    @PostMapping("/doctors/{id}/toggle-availability")
    public String toggleDoctorAvailability(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            doctorService.toggleAvailability(id);
            redirectAttributes.addFlashAttribute("message", "Doctor availability updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update doctor availability.");
        }
        return "redirect:/admin/doctors";
    }

    @PostMapping("/users/{id}/disable")
    public String disableUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.disableUser(id);
            redirectAttributes.addFlashAttribute("message", "User disabled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to disable user.");
        }
        return "redirect:/admin/patients";
    }

    @PostMapping("/users/{id}/enable")
    public String enableUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.enableUser(id);
            redirectAttributes.addFlashAttribute("message", "User enabled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to enable user.");
        }
        return "redirect:/admin/patients";
    }

    @GetMapping("/patients/{id}/edit")
    @ResponseBody
    public ResponseEntity<?> getPatientForEdit(@PathVariable Long id) {
        try {
            Optional<Patient> patientOpt = patientService.findById(id);
            if (patientOpt.isPresent()) {
                return ResponseEntity.ok(patientOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error loading patient: " + e.getMessage());
        }
    }

    @GetMapping("/doctors/{id}/edit")
    @ResponseBody
    public ResponseEntity<?> getDoctorForEdit(@PathVariable Long id) {
        try {
            Optional<Doctor> doctorOpt = doctorService.findById(id);
            if (doctorOpt.isPresent()) {
                return ResponseEntity.ok(doctorOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error loading doctor: " + e.getMessage());
        }
    }

    @PostMapping("/patients/{id}/update")
    @ResponseBody
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Map<String, Object> patientData) {
        try {
            Optional<Patient> existingPatientOpt = patientService.findById(id);
            if (existingPatientOpt.isPresent()) {
                Patient existingPatient = existingPatientOpt.get();

                // Update patient details
                if (patientData.containsKey("address")) {
                    existingPatient.setAddress((String) patientData.get("address"));
                }
                if (patientData.containsKey("bloodGroup") && !((String) patientData.get("bloodGroup")).isEmpty()) {
                    existingPatient.setBloodGroup((String) patientData.get("bloodGroup"));
                }
                if (patientData.containsKey("dateOfBirth")) {
                    String dateStr = (String) patientData.get("dateOfBirth");
                    existingPatient.setDateOfBirth(java.time.LocalDate.parse(dateStr));
                }
                if (patientData.containsKey("emergencyContact")) {
                    existingPatient.setEmergencyContact((String) patientData.get("emergencyContact"));
                }
                if (patientData.containsKey("gender")) {
                    String genderStr = (String) patientData.get("gender");
                    existingPatient.setGender(Patient.Gender.valueOf(genderStr));
                }
                if (patientData.containsKey("medicalHistory")) {
                    existingPatient.setMedicalHistory((String) patientData.get("medicalHistory"));
                }

                // Update user details (Patient extends User)
                if (patientData.containsKey("fullName")) {
                    existingPatient.setFullName((String) patientData.get("fullName"));
                }
                if (patientData.containsKey("email")) {
                    existingPatient.setEmail((String) patientData.get("email"));
                }
                if (patientData.containsKey("phoneNumber")) {
                    existingPatient.setPhoneNumber((String) patientData.get("phoneNumber"));
                }

                patientService.updatePatient(existingPatient);
                return ResponseEntity.ok("{\"success\": true, \"message\": \"Patient updated successfully\"}");
            } else {
                return ResponseEntity.status(404).body("{\"success\": false, \"message\": \"Patient not found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Add this for debugging
            return ResponseEntity.badRequest()
                    .body("{\"success\": false, \"message\": \"Error updating patient: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/api/patients/{id}/appointments")
    @ResponseBody
    public ResponseEntity<?> getPatientAppointments(@PathVariable Long id) {
        try {
            Optional<Patient> patientOpt = patientService.findById(id);
            if (patientOpt.isPresent()) {
                Patient patient = patientOpt.get();
                List<Appointment> appointments = appointmentService.findByPatient(patient);
                // Sort appointments by date (most recent first)
                appointments.sort((a1, a2) -> a2.getAppointmentDateTime().compareTo(a1.getAppointmentDateTime()));

                // Convert to DTOs to avoid circular reference issues
                List<AppointmentDto> appointmentDtos = appointments.stream()
                        .map(AppointmentDto::new)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(appointmentDtos);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error loading patient appointments: " + e.getMessage());
        }
    }

    @GetMapping("/api/patients/{id}/medical-records")
    @ResponseBody
    public ResponseEntity<?> getPatientMedicalRecords(@PathVariable Long id) {
        try {
            Optional<Patient> patientOpt = patientService.findById(id);
            if (patientOpt.isPresent()) {
                Patient patient = patientOpt.get();

                // Get recent appointments with prescriptions
                List<Appointment> appointments = appointmentService.findByPatient(patient);
                List<Map<String, String>> recentPrescriptions = appointments.stream()
                        .filter(appointment -> appointment.getPrescription() != null
                                && !appointment.getPrescription().trim().isEmpty())
                        .sorted((a1, a2) -> a2.getAppointmentDateTime().compareTo(a1.getAppointmentDateTime()))
                        .limit(10) // Get last 10 prescriptions
                        .map(appointment -> {
                            Map<String, String> prescription = new HashMap<>();
                            prescription.put("date", appointment.getAppointmentDateTime()
                                    .format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy")));
                            prescription.put("doctorName", appointment.getDoctor().getFullName());
                            prescription.put("prescription", appointment.getPrescription());
                            return prescription;
                        })
                        .collect(Collectors.toList());

                // Calculate age
                int age = java.time.Period.between(patient.getDateOfBirth(), java.time.LocalDate.now()).getYears();

                // Create response
                Map<String, Object> response = new HashMap<>();
                response.put("patientName", patient.getFullName());
                response.put("age", age);
                response.put("gender", patient.getGender().toString());
                response.put("bloodGroup", patient.getBloodGroup());
                response.put("emergencyContact", patient.getEmergencyContact());
                response.put("medicalHistory", patient.getMedicalHistory());
                response.put("recentPrescriptions", recentPrescriptions);

                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Patient not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error loading medical records: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/api/doctors/{id}/schedule")
    @ResponseBody
    public ResponseEntity<?> getDoctorSchedule(@PathVariable Long id) {
        try {
            Optional<Doctor> doctorOpt = doctorService.findById(id);
            if (doctorOpt.isPresent()) {
                Doctor doctor = doctorOpt.get();

                // Get doctor's appointments
                List<Appointment> allAppointments = appointmentService.findByDoctor(doctor);

                // Filter upcoming appointments (next 30 days)
                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                java.time.LocalDateTime next30Days = now.plusDays(30);

                List<Map<String, String>> upcomingAppointments = allAppointments.stream()
                        .filter(appointment -> appointment.getAppointmentDateTime().isAfter(now) &&
                                appointment.getAppointmentDateTime().isBefore(next30Days))
                        .sorted((a1, a2) -> a1.getAppointmentDateTime().compareTo(a2.getAppointmentDateTime()))
                        .limit(10) // Limit to next 10 appointments
                        .map(appointment -> {
                            Map<String, String> apt = new HashMap<>();
                            apt.put("appointmentDateTime", appointment.getAppointmentDateTime()
                                    .format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")));
                            apt.put("patientName", appointment.getPatient().getFullName());
                            apt.put("reason", appointment.getReason());
                            apt.put("status", appointment.getStatus().toString());
                            return apt;
                        })
                        .collect(Collectors.toList());

                // Calculate statistics
                java.time.LocalDate today = java.time.LocalDate.now();
                java.time.LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
                java.time.LocalDate endOfWeek = startOfWeek.plusDays(6);

                long todayAppointments = allAppointments.stream()
                        .filter(apt -> apt.getAppointmentDateTime().toLocalDate().equals(today))
                        .count();

                long thisWeekAppointments = allAppointments.stream()
                        .filter(apt -> {
                            java.time.LocalDate aptDate = apt.getAppointmentDateTime().toLocalDate();
                            return !aptDate.isBefore(startOfWeek) && !aptDate.isAfter(endOfWeek);
                        })
                        .count();

                // Count unique patients
                long totalPatients = allAppointments.stream()
                        .map(apt -> apt.getPatient().getId())
                        .distinct()
                        .count();

                // Create response
                Map<String, Object> response = new HashMap<>();
                response.put("doctorName", doctor.getFullName());
                response.put("specialization", doctor.getSpecialization());
                response.put("experienceYears", doctor.getExperienceYears());
                response.put("workingHours", doctor.getWorkingHours());
                response.put("consultationFee", doctor.getConsultationFee());
                response.put("availableForConsultation", doctor.isAvailableForConsultation());
                response.put("todayAppointments", todayAppointments);
                response.put("thisWeekAppointments", thisWeekAppointments);
                response.put("totalPatients", totalPatients);
                response.put("upcomingAppointments", upcomingAppointments);

                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Doctor not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error loading doctor schedule: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/doctors/{id}/update")
    @ResponseBody
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody Map<String, Object> doctorData) {
        try {
            Optional<Doctor> existingDoctorOpt = doctorService.findById(id);
            if (existingDoctorOpt.isPresent()) {
                Doctor existingDoctor = existingDoctorOpt.get();

                // Update doctor-specific details
                if (doctorData.containsKey("specialization")) {
                    existingDoctor.setSpecialization((String) doctorData.get("specialization"));
                }
                if (doctorData.containsKey("licenseNumber")) {
                    existingDoctor.setLicenseNumber((String) doctorData.get("licenseNumber"));
                }
                if (doctorData.containsKey("experienceYears")) {
                    Object expObj = doctorData.get("experienceYears");
                    if (expObj instanceof Integer) {
                        existingDoctor.setExperienceYears((Integer) expObj);
                    } else if (expObj instanceof String) {
                        existingDoctor.setExperienceYears(Integer.parseInt((String) expObj));
                    }
                }
                if (doctorData.containsKey("qualifications")) {
                    existingDoctor.setQualifications((String) doctorData.get("qualifications"));
                }
                if (doctorData.containsKey("consultationFee")) {
                    existingDoctor.setConsultationFee((String) doctorData.get("consultationFee"));
                }
                if (doctorData.containsKey("workingHours")) {
                    existingDoctor.setWorkingHours((String) doctorData.get("workingHours"));
                }
                if (doctorData.containsKey("availableForConsultation")) {
                    Object availObj = doctorData.get("availableForConsultation");
                    if (availObj instanceof Boolean) {
                        existingDoctor.setAvailableForConsultation((Boolean) availObj);
                    } else if (availObj instanceof String) {
                        existingDoctor.setAvailableForConsultation(Boolean.parseBoolean((String) availObj));
                    }
                }

                // Update user details (Doctor extends User)
                if (doctorData.containsKey("fullName")) {
                    existingDoctor.setFullName((String) doctorData.get("fullName"));
                }
                if (doctorData.containsKey("email")) {
                    existingDoctor.setEmail((String) doctorData.get("email"));
                }
                if (doctorData.containsKey("phoneNumber")) {
                    existingDoctor.setPhoneNumber((String) doctorData.get("phoneNumber"));
                }

                doctorService.updateDoctor(existingDoctor);
                return ResponseEntity.ok("{\"success\": true, \"message\": \"Doctor updated successfully\"}");
            } else {
                return ResponseEntity.status(404).body("{\"success\": false, \"message\": \"Doctor not found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Add this for debugging
            return ResponseEntity.badRequest()
                    .body("{\"success\": false, \"message\": \"Error updating doctor: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/api/appointments/{id}")
    @ResponseBody
    public ResponseEntity<?> getAppointment(@PathVariable Long id) {
        try {
            Optional<Appointment> appointmentOpt = appointmentService.findById(id);
            if (appointmentOpt.isPresent()) {
                Appointment appointment = appointmentOpt.get();
                AppointmentDetailDto appointmentDto = new AppointmentDetailDto(appointment);
                return ResponseEntity.ok(appointmentDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching appointment: " + e.getMessage());
        }
    }

    @PutMapping("/api/appointments/{id}")
    @ResponseBody
    public ResponseEntity<?> updateAppointment(@PathVariable Long id,
            @RequestBody Map<String, Object> appointmentData) {
        try {
            Optional<com.hms.entity.Appointment> existingAppointment = appointmentService.findById(id);
            if (!existingAppointment.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            com.hms.entity.Appointment appointment = existingAppointment.get();

            // Update appointment fields
            if (appointmentData.containsKey("patientId")) {
                Long patientId = Long.valueOf(appointmentData.get("patientId").toString());
                Optional<Patient> patient = patientService.findById(patientId);
                if (patient.isPresent()) {
                    appointment.setPatient(patient.get());
                }
            }

            if (appointmentData.containsKey("doctorId")) {
                Long doctorId = Long.valueOf(appointmentData.get("doctorId").toString());
                Optional<Doctor> doctor = doctorService.findById(doctorId);
                if (doctor.isPresent()) {
                    appointment.setDoctor(doctor.get());
                }
            }

            if (appointmentData.containsKey("appointmentDateTime")) {
                String dateTimeStr = appointmentData.get("appointmentDateTime").toString();
                appointment.setAppointmentDateTime(java.time.LocalDateTime.parse(dateTimeStr));
            }

            if (appointmentData.containsKey("status")) {
                String status = appointmentData.get("status").toString();
                appointment.setStatus(com.hms.entity.Appointment.AppointmentStatus.valueOf(status));
            }

            if (appointmentData.containsKey("reason")) {
                appointment.setReason(appointmentData.get("reason").toString());
            }

            if (appointmentData.containsKey("notes")) {
                appointment.setNotes(appointmentData.get("notes").toString());
            }

            if (appointmentData.containsKey("prescription")) {
                appointment.setPrescription(appointmentData.get("prescription").toString());
            }

            appointment.setUpdatedAt(java.time.LocalDateTime.now());

            appointmentService.updateAppointment(appointment);
            return ResponseEntity.ok().body("Appointment updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating appointment: " + e.getMessage());
        }
    }

    @PostMapping("/api/appointments/{id}/confirm")
    @ResponseBody
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id) {
        try {
            Optional<com.hms.entity.Appointment> appointment = appointmentService.findById(id);
            if (appointment.isPresent()) {
                com.hms.entity.Appointment apt = appointment.get();
                apt.setStatus(com.hms.entity.Appointment.AppointmentStatus.CONFIRMED);
                apt.setUpdatedAt(java.time.LocalDateTime.now());
                appointmentService.updateAppointment(apt);
                return ResponseEntity.ok().body("Appointment confirmed successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error confirming appointment: " + e.getMessage());
        }
    }

    @PostMapping("/api/appointments/{id}/cancel")
    @ResponseBody
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        try {
            Optional<com.hms.entity.Appointment> appointment = appointmentService.findById(id);
            if (appointment.isPresent()) {
                com.hms.entity.Appointment apt = appointment.get();
                apt.setStatus(com.hms.entity.Appointment.AppointmentStatus.CANCELLED);
                apt.setUpdatedAt(java.time.LocalDateTime.now());
                appointmentService.updateAppointment(apt);
                return ResponseEntity.ok().body("Appointment cancelled successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error cancelling appointment: " + e.getMessage());
        }
    }

    @PostMapping("/api/appointments")
    @ResponseBody
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, Object> appointmentData) {
        try {
            // Validate required fields
            if (!appointmentData.containsKey("patientId") || !appointmentData.containsKey("doctorId")
                    || !appointmentData.containsKey("appointmentDateTime")) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }

            // Get patient and doctor
            Long patientId = Long.valueOf(appointmentData.get("patientId").toString());
            Long doctorId = Long.valueOf(appointmentData.get("doctorId").toString());

            Optional<Patient> patient = patientService.findById(patientId);
            Optional<Doctor> doctor = doctorService.findById(doctorId);

            if (!patient.isPresent()) {
                return ResponseEntity.badRequest().body("Patient not found");
            }
            if (!doctor.isPresent()) {
                return ResponseEntity.badRequest().body("Doctor not found");
            }

            // Create new appointment
            com.hms.entity.Appointment appointment = new com.hms.entity.Appointment();
            appointment.setPatient(patient.get());
            appointment.setDoctor(doctor.get());

            String dateTimeStr = appointmentData.get("appointmentDateTime").toString();
            appointment.setAppointmentDateTime(java.time.LocalDateTime.parse(dateTimeStr));

            appointment.setStatus(com.hms.entity.Appointment.AppointmentStatus.SCHEDULED);

            if (appointmentData.containsKey("reason")) {
                appointment.setReason(appointmentData.get("reason").toString());
            }

            appointment.setCreatedAt(java.time.LocalDateTime.now());
            appointment.setUpdatedAt(java.time.LocalDateTime.now());

            appointmentService.saveAppointment(appointment);
            return ResponseEntity.ok().body("Appointment scheduled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating appointment: " + e.getMessage());
        }
    }

    @GetMapping("/api/patients")
    @ResponseBody
    public ResponseEntity<?> getPatients() {
        try {
            return ResponseEntity.ok(patientService.getActivePatients());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching patients: " + e.getMessage());
        }
    }

    @GetMapping("/api/doctors")
    @ResponseBody
    public ResponseEntity<?> getDoctors() {
        try {
            return ResponseEntity.ok(doctorService.getActiveDoctors());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching doctors: " + e.getMessage());
        }
    }
}