package com.hms.service;

import com.hms.entity.Appointment;
import com.hms.entity.Bill;
import com.hms.entity.Patient;
import com.hms.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillService {

    @Autowired
    private BillRepository billRepository;

    // Create a new bill
    public Bill createBill(Appointment appointment, Patient patient, BigDecimal consultationFee) {
        // Check if bill already exists for this appointment
        if (billRepository.existsByAppointmentId(appointment.getId())) {
            throw new RuntimeException("Bill already exists for this appointment");
        }

        Bill bill = new Bill(appointment, patient, consultationFee);
        return billRepository.save(bill);
    }

    // Create a comprehensive bill with all charges
    public Bill createBill(Appointment appointment, Patient patient,
            BigDecimal consultationFee, BigDecimal medicineCost,
            BigDecimal testCost, BigDecimal roomCharges,
            BigDecimal otherCharges, BigDecimal discount, String notes) {

        if (billRepository.existsByAppointmentId(appointment.getId())) {
            throw new RuntimeException("Bill already exists for this appointment");
        }

        Bill bill = new Bill(appointment, patient, consultationFee);
        bill.setMedicineCost(medicineCost);
        bill.setTestCost(testCost);
        bill.setRoomCharges(roomCharges);
        bill.setOtherCharges(otherCharges);
        bill.setDiscount(discount);
        bill.setNotes(notes);
        bill.calculateTotalAmount();

        return billRepository.save(bill);
    }

    // Get all bills
    public List<Bill> getAllBills() {
        return billRepository.findAllWithRelationships();
    }

    // Get bill by ID
    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }

    // Get bills for a specific patient
    public List<Bill> getBillsByPatient(Patient patient) {
        return billRepository.findByPatientOrderByBillDateDesc(patient);
    }

    // Get bills for a specific patient by ID
    public List<Bill> getBillsByPatientId(Long patientId) {
        return billRepository.findByPatientIdOrderByBillDateDesc(patientId);
    }

    // Get bill by appointment ID
    public Optional<Bill> getBillByAppointmentId(Long appointmentId) {
        return billRepository.findByAppointmentId(appointmentId);
    }

    // Update bill
    public Bill updateBill(Bill bill) {
        if (bill.getId() == null || !billRepository.existsById(bill.getId())) {
            throw new RuntimeException("Bill not found");
        }
        bill.calculateTotalAmount();
        return billRepository.save(bill);
    }

    // Update bill charges
    public Bill updateBillCharges(Long billId, BigDecimal consultationFee,
            BigDecimal medicineCost, BigDecimal testCost,
            BigDecimal roomCharges, BigDecimal otherCharges,
            BigDecimal discount, String notes) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setConsultationFee(consultationFee);
        bill.setMedicineCost(medicineCost);
        bill.setTestCost(testCost);
        bill.setRoomCharges(roomCharges);
        bill.setOtherCharges(otherCharges);
        bill.setDiscount(discount);
        bill.setNotes(notes);
        bill.calculateTotalAmount();

        return billRepository.save(bill);
    }

    // Mark bill as paid
    public Bill markAsPaid(Long billId, String paymentMethod) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setPaymentStatus(Bill.PaymentStatus.PAID);
        bill.setPaymentDate(LocalDateTime.now());
        bill.setPaymentMethod(paymentMethod);

        return billRepository.save(bill);
    }

    // Cancel bill
    public Bill cancelBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setPaymentStatus(Bill.PaymentStatus.CANCELLED);
        return billRepository.save(bill);
    }

    // Get unpaid bills for a patient
    public List<Bill> getUnpaidBillsByPatientId(Long patientId) {
        return billRepository.findUnpaidBillsByPatientId(patientId);
    }

    // Get total unpaid amount for a patient
    public Double getTotalUnpaidAmountByPatientId(Long patientId) {
        return billRepository.getTotalUnpaidAmountByPatientId(patientId);
    }

    // Get bills by payment status
    public List<Bill> getBillsByPaymentStatus(Bill.PaymentStatus paymentStatus) {
        return billRepository.findByPaymentStatusOrderByBillDateDesc(paymentStatus);
    }

    // Get bills by date range
    public List<Bill> getBillsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return billRepository.findByBillDateBetween(startDate, endDate);
    }

    // Get bills by doctor
    public List<Bill> getBillsByDoctorId(Long doctorId) {
        return billRepository.findByDoctorId(doctorId);
    }

    // Get recent bills (last 30 days)
    public List<Bill> getRecentBills() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return billRepository.findRecentBills(thirtyDaysAgo);
    }

    // Get bill statistics
    public BillStatistics getBillStatistics() {
        Long totalBills = billRepository.count();
        Long paidBills = billRepository.countByPaymentStatus(Bill.PaymentStatus.PAID);
        Long pendingBills = billRepository.countByPaymentStatus(Bill.PaymentStatus.PENDING);
        Double totalRevenue = billRepository.getTotalRevenue();

        return new BillStatistics(totalBills, paidBills, pendingBills, totalRevenue);
    }

    // Get monthly revenue
    public Double getMonthlyRevenue(int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
        return billRepository.getTotalRevenueByDateRange(startDate, endDate);
    }

    // Delete bill (admin only)
    public void deleteBill(Long billId) {
        if (!billRepository.existsById(billId)) {
            throw new RuntimeException("Bill not found");
        }
        billRepository.deleteById(billId);
    }

    // Check if appointment has bill
    public boolean hasAppointmentBill(Long appointmentId) {
        return billRepository.existsByAppointmentId(appointmentId);
    }

    // Generate bill for completed appointment
    public Bill generateBillForAppointment(Appointment appointment, BigDecimal consultationFee) {
        if (hasAppointmentBill(appointment.getId())) {
            return getBillByAppointmentId(appointment.getId()).orElse(null);
        }

        return createBill(appointment, appointment.getPatient(), consultationFee);
    }

    // Inner class for statistics
    public static class BillStatistics {
        private Long totalBills;
        private Long paidBills;
        private Long pendingBills;
        private Double totalRevenue;

        public BillStatistics(Long totalBills, Long paidBills, Long pendingBills, Double totalRevenue) {
            this.totalBills = totalBills;
            this.paidBills = paidBills;
            this.pendingBills = pendingBills;
            this.totalRevenue = totalRevenue;
        }

        // Getters
        public Long getTotalBills() {
            return totalBills;
        }

        public Long getPaidBills() {
            return paidBills;
        }

        public Long getPendingBills() {
            return pendingBills;
        }

        public Double getTotalRevenue() {
            return totalRevenue;
        }

        public String getFormattedTotalRevenue() {
            return totalRevenue != null ? String.format("%.2f", totalRevenue) : "0.00";
        }
    }
}