package com.dailyspend.dto;

import com.dailyspend.entity.TransactionCategory;
import com.dailyspend.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRequest {
    
    @NotNull(message = "Account ID is mandatory")
    private Long accountId;
    
    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Description is mandatory")
    private String description;
    
    @NotNull(message = "Transaction type is mandatory")
    private TransactionType transactionType;
    
    @NotNull(message = "Category is mandatory")
    private TransactionCategory category;
    
    private LocalDateTime transactionDate;
    
    private Boolean isBill = false;
    
    private String referenceNumber;
    
    private String modeOfPayment;
    
    private String comment;
    
    // Constructors
    public TransactionRequest() {
        this.transactionDate = LocalDateTime.now();
    }
    
    public TransactionRequest(Long accountId, BigDecimal amount, String description, 
                             TransactionType transactionType, TransactionCategory category) {
        this.accountId = accountId;
        this.amount = amount;
        this.description = description;
        this.transactionType = transactionType;
        this.category = category;
        this.transactionDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
}