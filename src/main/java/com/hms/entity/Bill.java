package com.hms.entity;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bills")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    @NotNull
    @JsonBackReference("appointment-bill")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    @JsonBackReference("patient-bills")
    private Patient patient;

    @Column(name = "consultation_fee", nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal consultationFee;

    @Column(name = "medicine_cost", precision = 10, scale = 2)
    private BigDecimal medicineCost = BigDecimal.ZERO;

    @Column(name = "test_cost", precision = 10, scale = 2)
    private BigDecimal testCost = BigDecimal.ZERO;

    @Column(name = "room_charges", precision = 10, scale = 2)
    private BigDecimal roomCharges = BigDecimal.ZERO;

    @Column(name = "other_charges", precision = 10, scale = 2)
    private BigDecimal otherCharges = BigDecimal.ZERO;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal totalAmount;

    @Column(name = "bill_date", nullable = false)
    @NotNull
    private LocalDateTime billDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    @NotNull
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Enum for payment status
    public enum PaymentStatus {
        PENDING, PAID, CANCELLED, REFUNDED
    }

    // Constructors
    public Bill() {
        this.createdAt = LocalDateTime.now();
        this.billDate = LocalDateTime.now();
    }

    public Bill(Appointment appointment, Patient patient, BigDecimal consultationFee) {
        this();
        this.appointment = appointment;
        this.patient = patient;
        this.consultationFee = consultationFee;
        calculateTotalAmount();
    }

    // Method to calculate total amount
    public void calculateTotalAmount() {
        BigDecimal total = consultationFee != null ? consultationFee : BigDecimal.ZERO;
        total = total.add(medicineCost != null ? medicineCost : BigDecimal.ZERO);
        total = total.add(testCost != null ? testCost : BigDecimal.ZERO);
        total = total.add(roomCharges != null ? roomCharges : BigDecimal.ZERO);
        total = total.add(otherCharges != null ? otherCharges : BigDecimal.ZERO);
        total = total.subtract(discount != null ? discount : BigDecimal.ZERO);
        this.totalAmount = total.max(BigDecimal.ZERO); // Ensure non-negative
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.billDate == null) {
            this.billDate = LocalDateTime.now();
        }
        calculateTotalAmount();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        calculateTotalAmount();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public BigDecimal getMedicineCost() {
        return medicineCost;
    }

    public void setMedicineCost(BigDecimal medicineCost) {
        this.medicineCost = medicineCost;
    }

    public BigDecimal getTestCost() {
        return testCost;
    }

    public void setTestCost(BigDecimal testCost) {
        this.testCost = testCost;
    }

    public BigDecimal getRoomCharges() {
        return roomCharges;
    }

    public void setRoomCharges(BigDecimal roomCharges) {
        this.roomCharges = roomCharges;
    }

    public BigDecimal getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(BigDecimal otherCharges) {
        this.otherCharges = otherCharges;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper methods
    public String getFormattedTotalAmount() {
        return totalAmount != null ? String.format("%.2f", totalAmount) : "0.00";
    }

    public String getFormattedConsultationFee() {
        return consultationFee != null ? String.format("%.2f", consultationFee) : "0.00";
    }

    public boolean isPaid() {
        return PaymentStatus.PAID.equals(paymentStatus);
    }

    public boolean isPending() {
        return PaymentStatus.PENDING.equals(paymentStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Bill bill = (Bill) o;
        return Objects.equals(id, bill.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", paymentStatus=" + paymentStatus +
                ", billDate=" + billDate +
                '}';
    }
}