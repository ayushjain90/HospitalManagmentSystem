package com.hms.repository;

import com.hms.entity.Appointment;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByStatus(Appointment.AppointmentStatus status);

    List<Appointment> findByPatientAndStatus(Patient patient, Appointment.AppointmentStatus status);

    List<Appointment> findByDoctorAndStatus(Doctor doctor, Appointment.AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDateTime BETWEEN ?1 AND ?2")
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.doctor = ?1 AND a.appointmentDateTime BETWEEN ?2 AND ?3")
    List<Appointment> findByDoctorAndAppointmentDateTimeBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.patient = ?1 AND a.appointmentDateTime BETWEEN ?2 AND ?3")
    List<Appointment> findByPatientAndAppointmentDateTimeBetween(Patient patient, LocalDateTime start,
            LocalDateTime end);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDateTime >= CURRENT_TIMESTAMP ORDER BY a.appointmentDateTime ASC")
    List<Appointment> findUpcomingAppointments();

    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.patient LEFT JOIN FETCH a.doctor ORDER BY a.appointmentDateTime DESC")
    List<Appointment> findAllWithPatientAndDoctor();

    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND a.appointmentDateTime >= CURRENT_TIMESTAMP AND a.status IN ('SCHEDULED', 'CONFIRMED') ORDER BY a.appointmentDateTime ASC")
    List<Appointment> findUpcomingAppointmentsByDoctor(@Param("doctor") Doctor doctor);

    @Query("SELECT a FROM Appointment a WHERE a.patient = :patient AND a.appointmentDateTime >= CURRENT_TIMESTAMP ORDER BY a.appointmentDateTime ASC")
    List<Appointment> findUpcomingAppointmentsByPatient(@Param("patient") Patient patient);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.status = 'SCHEDULED'")
    long countScheduledAppointments();

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.appointmentDateTime >= CURRENT_DATE AND a.appointmentDateTime < DATEADD(day, 1, CURRENT_DATE)")
    long countTodayAppointments();

    long countByStatus(Appointment.AppointmentStatus status);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.status = 'COMPLETED' AND a.appointmentDateTime >= CURRENT_DATE AND a.appointmentDateTime < DATEADD(day, 1, CURRENT_DATE)")
    long countCompletedToday();

    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.patient LEFT JOIN FETCH a.doctor WHERE a.status = 'COMPLETED' ORDER BY a.appointmentDateTime DESC")
    List<Appointment> findCompletedAppointmentsWithPatientAndDoctor();
}