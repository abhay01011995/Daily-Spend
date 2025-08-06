package com.dailyspend.service;

import com.dailyspend.dto.TransactionRequest;
import com.dailyspend.dto.TransactionResponse;
import com.dailyspend.entity.Account;
import com.dailyspend.entity.Transaction;
import com.dailyspend.entity.TransactionCategory;
import com.dailyspend.entity.TransactionType;
import com.dailyspend.repository.AccountRepository;
import com.dailyspend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public TransactionResponse addTransaction(TransactionRequest request) {
        // Validate account exists
        Optional<Account> accountOptional = accountRepository.findById(request.getAccountId());
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found with ID: " + request.getAccountId());
        }
        
        Account account = accountOptional.get();
        
        // Create transaction entity
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setCategory(request.getCategory());
        transaction.setTransactionDate(request.getTransactionDate() != null ? 
            request.getTransactionDate() : LocalDateTime.now());
        transaction.setIsBill(request.getIsBill() != null ? request.getIsBill() : false);
        transaction.setReferenceNumber(request.getReferenceNumber());
        
        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // Convert to response
        return convertToResponse(savedTransaction);
    }
    
    public List<TransactionResponse> getTransactionsByAccount(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<TransactionResponse> getTransactionsByAccountAndType(Long accountId, TransactionType transactionType) {
        List<Transaction> transactions = transactionRepository.findByAccountIdAndTransactionType(accountId, transactionType);
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<TransactionResponse> getBillsByAccount(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdAndIsBill(accountId, true);
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<TransactionResponse> getCurrentMonthTransactionsByAccount(Long accountId) {
        List<Transaction> transactions = transactionRepository.findCurrentMonthTransactionsByAccountId(accountId);
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<TransactionResponse> getTransactionsByDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = transactionRepository.findByAccountIdAndDateRange(accountId, startDate, endDate);
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public TransactionResponse getTransactionById(Long id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new RuntimeException("Transaction not found with ID: " + id);
        }
        return convertToResponse(transactionOptional.get());
    }
    
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with ID: " + id);
        }
        transactionRepository.deleteById(id);
    }
    
    // Helper method to convert Transaction entity to TransactionResponse DTO
    private TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAccountId(transaction.getAccount().getId());
        response.setAccountName(transaction.getAccount().getName());
        response.setAmount(transaction.getAmount());
        response.setDescription(transaction.getDescription());
        response.setTransactionType(transaction.getTransactionType());
        response.setCategory(transaction.getCategory());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setIsBill(transaction.getIsBill());
        response.setReferenceNumber(transaction.getReferenceNumber());
        // Note: modeOfPayment and comment are not stored in Transaction entity yet
        // They would need to be added to the Transaction entity if required
        response.setCreatedAt(transaction.getCreatedAt());
        response.setUpdatedAt(transaction.getUpdatedAt());
        return response;
    }
    
    // Utility methods for getting enum values
    public TransactionType[] getTransactionTypes() {
        return TransactionType.values();
    }
    
    public TransactionCategory[] getTransactionCategories() {
        return TransactionCategory.values();
    }
}