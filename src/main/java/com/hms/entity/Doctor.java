package com.hms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Doctor extends User {

    @NotBlank(message = "Specialization is required")
    @Column(nullable = false)
    private String specialization;

    @NotBlank(message = "License number is required")
    @Column(unique = true, nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private int experienceYears;

    @Column(length = 1000)
    private String qualifications;

    @Column(length = 500)
    private String consultationFee;

    @Column(nullable = false)
    private boolean availableForConsultation = true;

    @Column(length = 200)
    private String workingHours;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    // Constructors
    public Doctor() {
        super();
        setRole(Role.DOCTOR);
    }

    public Doctor(String username, String password, String email, String fullName,
            String phoneNumber, String specialization, String licenseNumber, int experienceYears) {
        super(username, password, email, fullName, phoneNumber, Role.DOCTOR);
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.experienceYears = experienceYears;
    }

    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }

    public boolean isAvailableForConsultation() {
        return availableForConsultation;
    }

    public void setAvailableForConsultation(boolean availableForConsultation) {
        this.availableForConsultation = availableForConsultation;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}