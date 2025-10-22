package com.hms.repository;

import com.hms.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUsername(String username);

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    List<Doctor> findByEnabledTrue();

    List<Doctor> findBySpecialization(String specialization);

    List<Doctor> findByAvailableForConsultationTrue();

    List<Doctor> findBySpecializationAndAvailableForConsultationTrue(String specialization);

    @Query("SELECT d FROM Doctor d WHERE d.fullName LIKE %?1% OR d.specialization LIKE %?1%")
    List<Doctor> searchByNameOrSpecialization(String keyword);

    @Query("SELECT DISTINCT d.specialization FROM Doctor d WHERE d.enabled = true")
    List<String> findAllSpecializations();

    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.enabled = true")
    long countActiveDoctors();

    long countByAvailableForConsultationTrueAndEnabledTrue();

    long countByAvailableForConsultationFalseAndEnabledTrue();

    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.enabled = true AND MONTH(d.createdAt) = MONTH(CURRENT_DATE) AND YEAR(d.createdAt) = YEAR(CURRENT_DATE)")
    long countNewDoctorsThisMonth();
}