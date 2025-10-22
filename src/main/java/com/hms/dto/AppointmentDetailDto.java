package com.hms.dto;

import com.hms.entity.Appointment;

public class AppointmentDetailDto {
    private Long id;
    private String patientName;
    private String doctorName;
    private String doctorSpecialization;
    private String appointmentDateTime;
    private String status;
    private String reason;
    private String notes;
    private String prescription;
    private Long patientId;
    private Long doctorId;

    public AppointmentDetailDto() {
    }

    public AppointmentDetailDto(Appointment appointment) {
        this.id = appointment.getId();
        this.patientName = appointment.getPatient().getFullName();
        this.doctorName = appointment.getDoctor().getFullName();
        this.doctorSpecialization = appointment.getDoctor().getSpecialization();
        this.appointmentDateTime = appointment.getAppointmentDateTime().toString();
        this.status = appointment.getStatus().toString();
        this.reason = appointment.getReason();
        this.notes = appointment.getNotes();
        this.prescription = appointment.getPrescription();
        this.patientId = appointment.getPatient().getId();
        this.doctorId = appointment.getDoctor().getId();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}