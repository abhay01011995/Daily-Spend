package com.dailyspend.service;

import com.dailyspend.dto.BudgetResponse;
import com.dailyspend.dto.DashboardResponse;
import com.dailyspend.dto.ExpenseSummaryResponse;
import com.dailyspend.entity.Account;
import com.dailyspend.entity.Transaction;
import com.dailyspend.entity.TransactionCategory;
import com.dailyspend.entity.TransactionType;
import com.dailyspend.repository.AccountRepository;
import com.dailyspend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * Get complete dashboard data for an account
     */
    public DashboardResponse getDashboardData(Long accountId) {
        // Validate account exists
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found with ID: " + accountId);
        }
        
        // Get all transactions for the account
        List<Transaction> allTransactions = transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
        
        // Calculate dashboard metrics
        BigDecimal totalSavings = calculateTotalSavings(allTransactions);
        BigDecimal totalInvestment = calculateTotalInvestment(allTransactions);
        BudgetResponse budgetTracker = calculateBudgetTracker(allTransactions);
        List<String> availableMonths = getAvailableMonths(allTransactions);
        String currentMonth = getCurrentMonth();
        List<ExpenseSummaryResponse> expenses = calculateExpenseSummary(allTransactions, currentMonth);
        
        return new DashboardResponse(totalSavings, totalInvestment, budgetTracker, 
                                   availableMonths, currentMonth, expenses);
    }
    
    /**
     * Get dashboard data for a specific month
     */
    public DashboardResponse getDashboardDataForMonth(Long accountId, String month) {
        // Validate account exists
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found with ID: " + accountId);
        }
        
        // Parse month and get date range
        YearMonth yearMonth = parseMonth(month);
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        
        // Get transactions for the month
        List<Transaction> monthTransactions = transactionRepository.findByAccountIdAndDateRange(accountId, startDate, endDate);
        List<Transaction> allTransactions = transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
        
        // Calculate metrics (some based on all time, some on current month)
        BigDecimal totalSavings = calculateTotalSavings(allTransactions);
        BigDecimal totalInvestment = calculateTotalInvestment(allTransactions);
        BudgetResponse budgetTracker = calculateBudgetTrackerForMonth(monthTransactions);
        List<String> availableMonths = getAvailableMonths(allTransactions);
        List<ExpenseSummaryResponse> expenses = calculateExpenseSummary(monthTransactions, month);
        
        return new DashboardResponse(totalSavings, totalInvestment, budgetTracker, 
                                   availableMonths, month, expenses);
    }
    
    /**
     * Calculate total savings (total income minus total expenses)
     */
    private BigDecimal calculateTotalSavings(List<Transaction> transactions) {
        BigDecimal totalIncome = transactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.INCOME)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal totalExpenses = transactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        return totalIncome.subtract(totalExpenses);
    }
    
    /**
     * Calculate total investment amount
     */
    private BigDecimal calculateTotalInvestment(List<Transaction> transactions) {
        return transactions.stream()
            .filter(t -> t.getCategory() == TransactionCategory.INVESTMENT)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Calculate budget tracker for current month
     */
    private BudgetResponse calculateBudgetTracker(List<Transaction> allTransactions) {
        // Get current month transactions
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
        
        List<Transaction> currentMonthTransactions = allTransactions.stream()
            .filter(t -> t.getTransactionDate().isAfter(startOfMonth.minusSeconds(1)) && 
                        t.getTransactionDate().isBefore(endOfMonth.plusSeconds(1)))
            .collect(Collectors.toList());
            
        return calculateBudgetTrackerForMonth(currentMonthTransactions);
    }
    
    /**
     * Calculate budget tracker for specific month transactions
     */
    private BudgetResponse calculateBudgetTrackerForMonth(List<Transaction> monthTransactions) {
        BigDecimal spent = monthTransactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        BigDecimal investment = monthTransactions.stream()
            .filter(t -> t.getCategory() == TransactionCategory.INVESTMENT)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        // For now, set a default budget of 100,000 (this could be made configurable per account)
        BigDecimal budget = new BigDecimal("100000.00");
        BigDecimal remaining = budget.subtract(spent);
        
        return new BudgetResponse(spent, budget, remaining, investment);
    }
    
    /**
     * Get list of available months from transactions
     */
    private List<String> getAvailableMonths(List<Transaction> transactions) {
        Set<String> monthSet = transactions.stream()
            .map(t -> {
                YearMonth yearMonth = YearMonth.from(t.getTransactionDate());
                return yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
            })
            .collect(Collectors.toSet());
            
        List<String> months = new ArrayList<>(monthSet);
        months.sort((a, b) -> {
            YearMonth ymA = YearMonth.parse(a, DateTimeFormatter.ofPattern("MMMM yyyy"));
            YearMonth ymB = YearMonth.parse(b, DateTimeFormatter.ofPattern("MMMM yyyy"));
            return ymB.compareTo(ymA); // Descending order (newest first)
        });
        
        return months;
    }
    
    /**
     * Get current month as string
     */
    private String getCurrentMonth() {
        return YearMonth.now().format(DateTimeFormatter.ofPattern("MMMM yyyy"));
    }
    
    /**
     * Calculate expense summary by category
     */
    private List<ExpenseSummaryResponse> calculateExpenseSummary(List<Transaction> transactions, String month) {
        Map<TransactionCategory, BigDecimal> categoryTotals = transactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
            .collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
            ));
            
        return categoryTotals.entrySet().stream()
            .map(entry -> new ExpenseSummaryResponse(
                entry.getKey().getDisplayName(),
                entry.getValue()
            ))
            .sorted((a, b) -> b.getAmount().compareTo(a.getAmount())) // Descending by amount
            .collect(Collectors.toList());
    }
    
    /**
     * Parse month string to YearMonth
     */
    private YearMonth parseMonth(String month) {
        try {
            return YearMonth.parse(month, DateTimeFormatter.ofPattern("MMMM yyyy"));
        } catch (Exception e) {
            throw new RuntimeException("Invalid month format: " + month + ". Expected format: 'MMMM yyyy' (e.g., 'August 2025')");
        }
    }
}