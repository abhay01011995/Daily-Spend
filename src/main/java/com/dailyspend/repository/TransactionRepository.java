package com.dailyspend.repository;

import com.dailyspend.entity.Transaction;
import com.dailyspend.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByAccountId(Long accountId);
    
    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);
    
    List<Transaction> findByAccountIdAndTransactionType(Long accountId, TransactionType transactionType);
    
    List<Transaction> findByAccountIdAndIsBill(Long accountId, Boolean isBill);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.transactionDate BETWEEN :startDate AND :endDate ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountIdAndDateRange(@Param("accountId") Long accountId, 
                                                 @Param("startDate") LocalDateTime startDate, 
                                                 @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND MONTH(t.transactionDate) = MONTH(CURRENT_DATE) AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE) ORDER BY t.transactionDate DESC")
    List<Transaction> findCurrentMonthTransactionsByAccountId(@Param("accountId") Long accountId);
}