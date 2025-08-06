package com.dailyspend.repository;

import com.dailyspend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByEmailAddress(String emailAddress);
    
    Optional<Account> findByMobileNumber(String mobileNumber);
    
    boolean existsByEmailAddress(String emailAddress);
    
    boolean existsByMobileNumber(String mobileNumber);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.account.id = :accountId")
    Integer countTotalTransactionsByAccountId(@Param("accountId") Long accountId);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.account.id = :accountId AND MONTH(t.transactionDate) = MONTH(CURRENT_DATE) AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE)")
    Integer countCurrentMonthTransactionsByAccountId(@Param("accountId") Long accountId);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.account.id = :accountId AND t.isBill = true")
    Integer countTotalBillsByAccountId(@Param("accountId") Long accountId);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.account.id = :accountId AND t.isBill = true AND MONTH(t.transactionDate) = MONTH(CURRENT_DATE) AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE)")
    Integer countCurrentMonthBillsByAccountId(@Param("accountId") Long accountId);
}