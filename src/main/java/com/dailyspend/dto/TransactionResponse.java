package com.dailyspend.dto;

import com.dailyspend.entity.TransactionCategory;
import com.dailyspend.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    
    private Long id;
    private Long accountId;
    private String accountName;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private TransactionCategory category;
    private LocalDateTime transactionDate;
    private Boolean isBill;
    private String referenceNumber;
    private String modeOfPayment;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public TransactionResponse() {}
    
    public TransactionResponse(Long id, Long accountId, String accountName, BigDecimal amount, 
                              String description, TransactionType transactionType, 
                              TransactionCategory category, LocalDateTime transactionDate) {
        this.id = id;
        this.accountId = accountId;
        this.accountName = accountName;
        this.amount = amount;
        this.description = description;
        this.transactionType = transactionType;
        this.category = category;
        this.transactionDate = transactionDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public TransactionType getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    
    public TransactionCategory getCategory() {
        return category;
    }
    
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }
    
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public Boolean getIsBill() {
        return isBill;
    }
    
    public void setIsBill(Boolean isBill) {
        this.isBill = isBill;
    }
    
    public String getReferenceNumber() {
        return referenceNumber;
    }
    
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public String getModeOfPayment() {
        return modeOfPayment;
    }
    
    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
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