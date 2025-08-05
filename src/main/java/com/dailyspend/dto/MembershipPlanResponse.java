package com.dailyspend.dto;

import com.dailyspend.entity.BillingCycle;
import com.dailyspend.entity.PlanType;

import java.math.BigDecimal;

public class MembershipPlanResponse {
    
    private Long id;
    private String planName;
    private String description;
    private PlanType planType;
    private BillingCycle billingCycle;
    private BigDecimal price;
    private Boolean isActive;
    
    // Constructors
    public MembershipPlanResponse() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPlanName() {
        return planName;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PlanType getPlanType() {
        return planType;
    }
    
    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }
    
    public BillingCycle getBillingCycle() {
        return billingCycle;
    }
    
    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}