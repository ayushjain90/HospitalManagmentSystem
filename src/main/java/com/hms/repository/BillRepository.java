package com.hms.repository;

import com.hms.entity.Bill;
import com.hms.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    // Find all bills for a specific patient
    List<Bill> findByPatientOrderByBillDateDesc(Patient patient);

    // Find all bills for a specific patient by patient ID
    List<Bill> findByPatientIdOrderByBillDateDesc(Long patientId);

    // Find bill by appointment ID
    Optional<Bill> findByAppointmentId(Long appointmentId);

    // Find bills by payment status
    List<Bill> findByPaymentStatusOrderByBillDateDesc(Bill.PaymentStatus paymentStatus);

    // Find bills by date range
    @Query("SELECT b FROM Bill b WHERE b.billDate BETWEEN :startDate AND :endDate ORDER BY b.billDate DESC")
    List<Bill> findByBillDateBetween(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Find unpaid bills for a patient
    @Query("SELECT b FROM Bill b WHERE b.patient.id = :patientId AND b.paymentStatus = 'PENDING' ORDER BY b.billDate DESC")
    List<Bill> findUnpaidBillsByPatientId(@Param("patientId") Long patientId);

    // Find total amount of unpaid bills for a patient
    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.patient.id = :patientId AND b.paymentStatus = 'PENDING'")
    Double getTotalUnpaidAmountByPatientId(@Param("patientId") Long patientId);

    // Find bills by doctor (through appointment)
    @Query("SELECT b FROM Bill b WHERE b.appointment.doctor.id = :doctorId ORDER BY b.billDate DESC")
    List<Bill> findByDoctorId(@Param("doctorId") Long doctorId);

    // Get recent bills (last 30 days)
    @Query("SELECT b FROM Bill b WHERE b.billDate >= :date ORDER BY b.billDate DESC")
    List<Bill> findRecentBills(@Param("date") LocalDateTime date);

    // Count bills by status
    @Query("SELECT COUNT(b) FROM Bill b WHERE b.paymentStatus = :status")
    Long countByPaymentStatus(@Param("status") Bill.PaymentStatus status);

    // Get total revenue (paid bills)
    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.paymentStatus = 'PAID'")
    Double getTotalRevenue();

    // Get total revenue for a date range
    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.paymentStatus = 'PAID' AND b.paymentDate BETWEEN :startDate AND :endDate")
    Double getTotalRevenueByDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Check if appointment has a bill
    boolean existsByAppointmentId(Long appointmentId);

    // Find top patients by total bill amount
    @Query("SELECT b.patient, SUM(b.totalAmount) as totalAmount FROM Bill b GROUP BY b.patient ORDER BY totalAmount DESC")
    List<Object[]> findTopPatientsByTotalAmount();

    // Find all bills with patient, appointment, and doctor relationships loaded
    @Query("SELECT b FROM Bill b LEFT JOIN FETCH b.patient LEFT JOIN FETCH b.appointment a LEFT JOIN FETCH a.doctor ORDER BY b.billDate DESC")
    List<Bill> findAllWithRelationships();
}