package com.dailyspend.entity;

public enum TransactionCategory {
    FOOD("Food"),
    TRANSPORTATION("Transportation"),
    ENTERTAINMENT("Entertainment"),
    SHOPPING("Shopping"),
    BILLS("Bills"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    SALARY("Salary"),
    BUSINESS("Business"),
    INVESTMENT("Investment"),
    OTHER("Other");
    
    private final String displayName;
    
    TransactionCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}