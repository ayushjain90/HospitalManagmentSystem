package com.hms.service;

import com.hms.entity.Doctor;
import com.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> getActiveDoctors() {
        return doctorRepository.findByEnabledTrue();
    }

    public List<Doctor> getAvailableDoctors() {
        return doctorRepository.findByAvailableForConsultationTrue();
    }

    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    public Optional<Doctor> findByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }

    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public Optional<Doctor> findByLicenseNumber(String licenseNumber) {
        return doctorRepository.findByLicenseNumber(licenseNumber);
    }

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getPassword() != null && !doctor.getPassword().startsWith("$2a$")) {
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        }
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> findBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public List<Doctor> findAvailableDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationAndAvailableForConsultationTrue(specialization);
    }

    public List<Doctor> searchDoctors(String keyword) {
        return doctorRepository.searchByNameOrSpecialization(keyword);
    }

    public List<String> getAllSpecializations() {
        return doctorRepository.findAllSpecializations();
    }

    public long getActiveDoctorCount() {
        return doctorRepository.countActiveDoctors();
    }

    public long getAvailableDoctorCount() {
        return doctorRepository.countByAvailableForConsultationTrueAndEnabledTrue();
    }

    public long getBusyDoctorCount() {
        return doctorRepository.countByAvailableForConsultationFalseAndEnabledTrue();
    }

    public long getNewDoctorsThisMonth() {
        return doctorRepository.countNewDoctorsThisMonth();
    }

    public void toggleAvailability(Long doctorId) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            doctor.setAvailableForConsultation(!doctor.isAvailableForConsultation());
            doctorRepository.save(doctor);
        }
    }

    public Doctor registerNewDoctor(Doctor doctor) {
        doctor.setRole(Doctor.Role.DOCTOR);
        doctor.setEnabled(true);
        doctor.setAvailableForConsultation(true);
        return saveDoctor(doctor);
    }
}