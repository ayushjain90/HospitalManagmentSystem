package com.hms.service;

import com.hms.entity.Patient;
import com.hms.entity.User;
import com.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> getActivePatients() {
        return patientRepository.findByEnabledTrue();
    }

    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public Optional<Patient> findByUsername(String username) {
        return patientRepository.findByUsername(username);
    }

    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient savePatient(Patient patient) {
        if (patient.getPassword() != null && !patient.getPassword().startsWith("$2a$")) {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        }
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> searchPatients(String keyword) {
        return patientRepository.searchByNameOrUsername(keyword);
    }

    public List<Patient> findByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }

    public List<Patient> findByGender(Patient.Gender gender) {
        return patientRepository.findByGender(gender);
    }

    public long getActivePatientCount() {
        return patientRepository.countActivePatients();
    }

    public long getNewPatientsThisMonth() {
        return patientRepository.countNewPatientsThisMonth();
    }

    public Patient registerNewPatient(Patient patient) {
        patient.setRole(Patient.Role.PATIENT);
        patient.setEnabled(true);
        return savePatient(patient);
    }

    // Find patient by user
    public Optional<Patient> findByUser(User user) {
        return patientRepository.findByUsername(user.getUsername());
    }
}