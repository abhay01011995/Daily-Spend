package com.dailyspend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String emailAddress;
    
    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be 10 digits")
    @Column(nullable = false, unique = true)
    private String mobileNumber;
    
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(length = 3, columnDefinition = "varchar(3) default 'INR'")
    private String currencyCode = "INR";
    
    @Column(columnDefinition = "int default 0")
    private Integer totalTransactions = 0;
    
    @Column(columnDefinition = "int default 0")
    private Integer currentMonthTransactions = 0;
    
    @Column(columnDefinition = "int default 0")
    private Integer totalBills = 0;
    
    @Column(columnDefinition = "int default 0")
    private Integer currentMonthBills = 0;
    
    @Column(columnDefinition = "int default 0")
    private Integer profileCompletionPercentage = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_plan_id")
    private MembershipPlan membershipPlan;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Constructors
    public Account() {}
    
    public Account(String name, String mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    
    public Integer getTotalTransactions() {
        return totalTransactions;
    }
    
    public void setTotalTransactions(Integer totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
    
    public Integer getCurrentMonthTransactions() {
        return currentMonthTransactions;
    }
    
    public void setCurrentMonthTransactions(Integer currentMonthTransactions) {
        this.currentMonthTransactions = currentMonthTransactions;
    }
    
    public Integer getTotalBills() {
        return totalBills;
    }
    
    public void setTotalBills(Integer totalBills) {
        this.totalBills = totalBills;
    }
    
    public Integer getCurrentMonthBills() {
        return currentMonthBills;
    }
    
    public void setCurrentMonthBills(Integer currentMonthBills) {
        this.currentMonthBills = currentMonthBills;
    }
    
    public Integer getProfileCompletionPercentage() {
        return profileCompletionPercentage;
    }
    
    public void setProfileCompletionPercentage(Integer profileCompletionPercentage) {
        this.profileCompletionPercentage = profileCompletionPercentage;
    }
    
    public MembershipPlan getMembershipPlan() {
        return membershipPlan;
    }
    
    public void setMembershipPlan(MembershipPlan membershipPlan) {
        this.membershipPlan = membershipPlan;
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
}