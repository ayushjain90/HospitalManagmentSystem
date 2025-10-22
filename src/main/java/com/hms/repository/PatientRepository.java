package com.hms.repository;

import com.hms.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUsername(String username);

    Optional<Patient> findByEmail(String email);

    List<Patient> findByEnabledTrue();

    @Query("SELECT p FROM Patient p WHERE p.fullName LIKE %?1% OR p.username LIKE %?1%")
    List<Patient> searchByNameOrUsername(String keyword);

    List<Patient> findByBloodGroup(String bloodGroup);

    List<Patient> findByGender(Patient.Gender gender);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.enabled = true")
    long countActivePatients();

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.enabled = true AND MONTH(p.createdAt) = MONTH(CURRENT_DATE) AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)")
    long countNewPatientsThisMonth();
}