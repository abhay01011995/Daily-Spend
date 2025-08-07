package com.dailyspend.dto;

import java.math.BigDecimal;

public class BudgetResponse {
    
    private BigDecimal spent;
    private BigDecimal budget;
    private BigDecimal remaining;
    private BigDecimal investment;
    
    // Constructors
    public BudgetResponse() {}
    
    public BudgetResponse(BigDecimal spent, BigDecimal budget, BigDecimal remaining, BigDecimal investment) {
        this.spent = spent;
        this.budget = budget;
        this.remaining = remaining;
        this.investment = investment;
    }
    
    // Getters and Setters
    public BigDecimal getSpent() {
        return spent;
    }
    
    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }
    
    public BigDecimal getBudget() {
        return budget;
    }
    
    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
    
    public BigDecimal getRemaining() {
        return remaining;
    }
    
    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }
    
    public BigDecimal getInvestment() {
        return investment;
    }
    
    public void setInvestment(BigDecimal investment) {
        this.investment = investment;
    }
}