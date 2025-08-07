package com.dailyspend.dto;

import java.math.BigDecimal;

public class ExpenseSummaryResponse {
    
    private String category;
    private BigDecimal amount;
    private String icon; // For UI representation
    
    // Constructors
    public ExpenseSummaryResponse() {}
    
    public ExpenseSummaryResponse(String category, BigDecimal amount, String icon) {
        this.category = category;
        this.amount = amount;
        this.icon = icon;
    }
    
    public ExpenseSummaryResponse(String category, BigDecimal amount) {
        this.category = category;
        this.amount = amount;
        this.icon = getDefaultIcon(category);
    }
    
    // Getters and Setters
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    // Helper method to set default icons based on category
    private String getDefaultIcon(String category) {
        if (category == null) return "🔍";
        
        switch (category.toLowerCase()) {
            case "shopping":
                return "🛒";
            case "food":
                return "🍽️";
            case "transportation":
                return "🚗";
            case "entertainment":
                return "🎬";
            case "bills":
                return "📄";
            case "healthcare":
                return "🏥";
            case "education":
                return "📚";
            case "investment":
                return "💰";
            case "hand loan":
            case "loan":
                return "💳";
            default:
                return "💸";
        }
    }
}