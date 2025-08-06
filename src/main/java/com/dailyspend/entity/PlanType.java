package com.dailyspend.entity;

public enum PlanType {
    BASIC("Basic"),
    SILVER("Silver"),
    GOLD("Gold"),
    PLATINUM("Platinum");
    
    private final String displayName;
    
    PlanType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}