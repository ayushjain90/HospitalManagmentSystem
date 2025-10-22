package com.hms.entity;

import javax.persistence.*;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminLevel adminLevel = AdminLevel.STANDARD;

    public enum AdminLevel {
        SUPER_ADMIN, STANDARD, LIMITED
    }

    // Constructors
    public Admin() {
        super();
        setRole(Role.ADMIN);
    }

    public Admin(String username, String password, String email, String fullName,
            String phoneNumber, String department, String employeeId) {
        super(username, password, email, fullName, phoneNumber, Role.ADMIN);
        this.department = department;
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public AdminLevel getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(AdminLevel adminLevel) {
        this.adminLevel = adminLevel;
    }
}