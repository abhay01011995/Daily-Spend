package com.dailyspend.dto;

import com.dailyspend.entity.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AccountResponse {
    
    private Long id;
    private String name;
    private String emailAddress;
    private String mobileNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String currencyCode;
    private Integer totalTransactions;
    private Integer currentMonthTransactions;
    private Integer totalBills;
    private Integer currentMonthBills;
    private Integer profileCompletionPercentage;
    private MembershipPlanResponse membershipPlan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public AccountResponse() {}
    
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
    
    public MembershipPlanResponse getMembershipPlan() {
        return membershipPlan;
    }
    
    public void setMembershipPlan(MembershipPlanResponse membershipPlan) {
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