package com.dailyspend.controller;

import com.dailyspend.dto.TransactionRequest;
import com.dailyspend.dto.TransactionResponse;
import com.dailyspend.entity.TransactionCategory;
import com.dailyspend.entity.TransactionType;
import com.dailyspend.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    /**
     * Add a new spend/income transaction
     * POST /transactions
     */
    @PostMapping
    public ResponseEntity<TransactionResponse> addTransaction(@Valid @RequestBody TransactionRequest request) {
        try {
            TransactionResponse response = transactionService.addTransaction(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Get all transactions for a specific account
     * GET /transactions/account/{accountId}
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccount(@PathVariable Long accountId) {
        try {
            List<TransactionResponse> transactions = transactionService.getTransactionsByAccount(accountId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get transactions by account and type (INCOME, EXPENSE, TRANSFER)
     * GET /transactions/account/{accountId}/type/{transactionType}
     */
    @GetMapping("/account/{accountId}/type/{transactionType}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountAndType(
            @PathVariable Long accountId, 
            @PathVariable TransactionType transactionType) {
        try {
            List<TransactionResponse> transactions = transactionService.getTransactionsByAccountAndType(accountId, transactionType);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get all bills for a specific account
     * GET /transactions/account/{accountId}/bills
     */
    @GetMapping("/account/{accountId}/bills")
    public ResponseEntity<List<TransactionResponse>> getBillsByAccount(@PathVariable Long accountId) {
        try {
            List<TransactionResponse> bills = transactionService.getBillsByAccount(accountId);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get current month transactions for a specific account
     * GET /transactions/account/{accountId}/current-month
     */
    @GetMapping("/account/{accountId}/current-month")
    public ResponseEntity<List<TransactionResponse>> getCurrentMonthTransactionsByAccount(@PathVariable Long accountId) {
        try {
            List<TransactionResponse> transactions = transactionService.getCurrentMonthTransactionsByAccount(accountId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get transactions by date range for a specific account
     * GET /transactions/account/{accountId}/date-range?startDate=2023-01-01T00:00:00&endDate=2023-12-31T23:59:59
     */
    @GetMapping("/account/{accountId}/date-range")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByDateRange(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<TransactionResponse> transactions = transactionService.getTransactionsByDateRange(accountId, startDate, endDate);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Get a specific transaction by ID
     * GET /transactions/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        try {
            TransactionResponse transaction = transactionService.getTransactionById(id);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Delete a transaction by ID
     * DELETE /transactions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Transaction not found", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get all available transaction types
     * GET /transactions/types
     */
    @GetMapping("/types")
    public ResponseEntity<TransactionType[]> getTransactionTypes() {
        TransactionType[] types = transactionService.getTransactionTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }
    
    /**
     * Get all available transaction categories
     * GET /transactions/categories
     */
    @GetMapping("/categories")
    public ResponseEntity<TransactionCategory[]> getTransactionCategories() {
        TransactionCategory[] categories = transactionService.getTransactionCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}