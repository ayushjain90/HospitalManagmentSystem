package com.hms.service;

import com.hms.entity.Appointment;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAllWithPatientAndDoctor();
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> findByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }

    public List<Appointment> findByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }

    public List<Appointment> findByStatus(Appointment.AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getUpcomingAppointments() {
        return appointmentRepository.findUpcomingAppointments();
    }

    public List<Appointment> getUpcomingAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findUpcomingAppointmentsByDoctor(doctor);
    }

    public List<Appointment> getUpcomingAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findUpcomingAppointmentsByPatient(patient);
    }

    public List<Appointment> getAppointmentsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAppointmentDateTimeBetween(start, end);
    }

    public List<Appointment> getDoctorAppointmentsBetweenDates(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorAndAppointmentDateTimeBetween(doctor, start, end);
    }

    public List<Appointment> getPatientAppointmentsBetweenDates(Patient patient, LocalDateTime start,
            LocalDateTime end) {
        return appointmentRepository.findByPatientAndAppointmentDateTimeBetween(patient, start, end);
    }

    public long getScheduledAppointmentCount() {
        return appointmentRepository.countScheduledAppointments();
    }

    public long getTodayAppointmentCount() {
        return appointmentRepository.countTodayAppointments();
    }

    public long getPendingConfirmationCount() {
        return appointmentRepository.countByStatus(Appointment.AppointmentStatus.SCHEDULED);
    }

    public long getCompletedTodayCount() {
        return appointmentRepository.countCompletedToday();
    }

    public Appointment scheduleAppointment(Patient patient, Doctor doctor, LocalDateTime appointmentDateTime,
            String reason) {
        Appointment appointment = new Appointment(patient, doctor, appointmentDateTime, reason);
        appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
        return saveAppointment(appointment);
    }

    public void confirmAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
            appointmentRepository.save(appointment);
        }
    }

    public void cancelAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
            appointmentRepository.save(appointment);
        }
    }

    public void rejectAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
            appointmentRepository.save(appointment);
        }
    }

    public void completeAppointment(Long appointmentId, String notes, String prescription) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
            appointment.setNotes(notes);
            appointment.setPrescription(prescription);
            appointmentRepository.save(appointment);
        }
    }

    public boolean isSlotAvailable(Doctor doctor, LocalDateTime appointmentDateTime) {
        LocalDateTime startTime = appointmentDateTime.minusMinutes(30);
        LocalDateTime endTime = appointmentDateTime.plusMinutes(30);

        List<Appointment> conflictingAppointments = appointmentRepository
                .findByDoctorAndAppointmentDateTimeBetween(doctor, startTime, endTime)
                .stream()
                .filter(a -> a.getStatus() == Appointment.AppointmentStatus.SCHEDULED ||
                        a.getStatus() == Appointment.AppointmentStatus.CONFIRMED)
                .collect(Collectors.toList());

        return conflictingAppointments.isEmpty();
    }

    // Get completed appointments that can have bills generated
    public List<Appointment> getCompletedAppointments() {
        return appointmentRepository.findCompletedAppointmentsWithPatientAndDoctor();
    }
}