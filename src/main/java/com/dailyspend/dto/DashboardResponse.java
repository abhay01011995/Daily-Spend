package com.dailyspend.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {
    
    private BigDecimal totalSavings;
    private BigDecimal totalInvestment;
    private BudgetResponse budgetTracker;
    private List<String> availableMonths;
    private String currentMonth;
    private List<ExpenseSummaryResponse> expenses;
    
    // Constructors
    public DashboardResponse() {}
    
    public DashboardResponse(BigDecimal totalSavings, BigDecimal totalInvestment, 
                           BudgetResponse budgetTracker, List<String> availableMonths,
                           String currentMonth, List<ExpenseSummaryResponse> expenses) {
        this.totalSavings = totalSavings;
        this.totalInvestment = totalInvestment;
        this.budgetTracker = budgetTracker;
        this.availableMonths = availableMonths;
        this.currentMonth = currentMonth;
        this.expenses = expenses;
    }
    
    // Getters and Setters
    public BigDecimal getTotalSavings() {
        return totalSavings;
    }
    
    public void setTotalSavings(BigDecimal totalSavings) {
        this.totalSavings = totalSavings;
    }
    
    public BigDecimal getTotalInvestment() {
        return totalInvestment;
    }
    
    public void setTotalInvestment(BigDecimal totalInvestment) {
        this.totalInvestment = totalInvestment;
    }
    
    public BudgetResponse getBudgetTracker() {
        return budgetTracker;
    }
    
    public void setBudgetTracker(BudgetResponse budgetTracker) {
        this.budgetTracker = budgetTracker;
    }
    
    public List<String> getAvailableMonths() {
        return availableMonths;
    }
    
    public void setAvailableMonths(List<String> availableMonths) {
        this.availableMonths = availableMonths;
    }
    
    public String getCurrentMonth() {
        return currentMonth;
    }
    
    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }
    
    public List<ExpenseSummaryResponse> getExpenses() {
        return expenses;
    }
    
    public void setExpenses(List<ExpenseSummaryResponse> expenses) {
        this.expenses = expenses;
    }
}