package com.dailyspend.service;

import com.dailyspend.dto.AccountRequest;
import com.dailyspend.dto.AccountResponse;
import com.dailyspend.dto.MembershipPlanResponse;
import com.dailyspend.entity.Account;
import com.dailyspend.entity.MembershipPlan;
import com.dailyspend.repository.AccountRepository;
import com.dailyspend.repository.MembershipPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private MembershipPlanRepository membershipPlanRepository;
    
    public AccountResponse createAccount(AccountRequest request) {
        // Check if email already exists
        if (request.getEmailAddress() != null && accountRepository.existsByEmailAddress(request.getEmailAddress())) {
            throw new RuntimeException("Email address already exists");
        }
        
        // Check if mobile number already exists
        if (accountRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }
        
        Account account = new Account();
        mapRequestToEntity(request, account);
        
        // Set membership plan if provided
        if (request.getMembershipPlanId() != null) {
            Optional<MembershipPlan> membershipPlan = membershipPlanRepository.findById(request.getMembershipPlanId());
            membershipPlan.ifPresent(account::setMembershipPlan);
        }
        
        // Calculate profile completion percentage
        account.setProfileCompletionPercentage(calculateProfileCompletion(account));
        
        Account savedAccount = accountRepository.save(account);
        return mapEntityToResponse(savedAccount);
    }
    
    public AccountResponse updateAccount(Long accountId, AccountRequest request) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        
        // Check email uniqueness if email is being updated
        if (request.getEmailAddress() != null && !request.getEmailAddress().equals(account.getEmailAddress())) {
            if (accountRepository.existsByEmailAddress(request.getEmailAddress())) {
                throw new RuntimeException("Email address already exists");
            }
        }
        
        // Check mobile number uniqueness if mobile is being updated
        if (request.getMobileNumber() != null && !request.getMobileNumber().equals(account.getMobileNumber())) {
            if (accountRepository.existsByMobileNumber(request.getMobileNumber())) {
                throw new RuntimeException("Mobile number already exists");
            }
        }
        
        mapRequestToEntity(request, account);
        
        // Update membership plan if provided
        if (request.getMembershipPlanId() != null) {
            Optional<MembershipPlan> membershipPlan = membershipPlanRepository.findById(request.getMembershipPlanId());
            membershipPlan.ifPresent(account::setMembershipPlan);
        }
        
        // Update profile completion percentage
        account.setProfileCompletionPercentage(calculateProfileCompletion(account));
        
        Account updatedAccount = accountRepository.save(account);
        return mapEntityToResponse(updatedAccount);
    }
    
    public AccountResponse getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        
        // Update transaction counts
        updateTransactionCounts(account);
        
        return mapEntityToResponse(account);
    }
    
    public AccountResponse getAccountByEmail(String emailAddress) {
        Account account = accountRepository.findByEmailAddress(emailAddress)
            .orElseThrow(() -> new RuntimeException("Account not found with email: " + emailAddress));
        
        // Update transaction counts
        updateTransactionCounts(account);
        
        return mapEntityToResponse(account);
    }
    
    public AccountResponse getAccountByMobile(String mobileNumber) {
        Account account = accountRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new RuntimeException("Account not found with mobile: " + mobileNumber));
        
        // Update transaction counts
        updateTransactionCounts(account);
        
        return mapEntityToResponse(account);
    }
    
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
            .map(this::mapEntityToResponse)
            .collect(Collectors.toList());
    }
    
    public void deleteAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
        accountRepository.deleteById(accountId);
    }
    
    private void mapRequestToEntity(AccountRequest request, Account account) {
        account.setName(request.getName());
        account.setEmailAddress(request.getEmailAddress());
        account.setMobileNumber(request.getMobileNumber());
        account.setDateOfBirth(request.getDateOfBirth());
        account.setGender(request.getGender());
        
        if (request.getCurrencyCode() != null) {
            account.setCurrencyCode(request.getCurrencyCode());
        }
    }
    
    private AccountResponse mapEntityToResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setName(account.getName());
        response.setEmailAddress(account.getEmailAddress());
        response.setMobileNumber(account.getMobileNumber());
        response.setDateOfBirth(account.getDateOfBirth());
        response.setGender(account.getGender());
        response.setCurrencyCode(account.getCurrencyCode());
        response.setTotalTransactions(account.getTotalTransactions());
        response.setCurrentMonthTransactions(account.getCurrentMonthTransactions());
        response.setTotalBills(account.getTotalBills());
        response.setCurrentMonthBills(account.getCurrentMonthBills());
        response.setProfileCompletionPercentage(account.getProfileCompletionPercentage());
        response.setCreatedAt(account.getCreatedAt());
        response.setUpdatedAt(account.getUpdatedAt());
        
        if (account.getMembershipPlan() != null) {
            response.setMembershipPlan(mapMembershipPlanToResponse(account.getMembershipPlan()));
        }
        
        return response;
    }
    
    private MembershipPlanResponse mapMembershipPlanToResponse(MembershipPlan membershipPlan) {
        MembershipPlanResponse response = new MembershipPlanResponse();
        response.setId(membershipPlan.getId());
        response.setPlanName(membershipPlan.getPlanName());
        response.setDescription(membershipPlan.getDescription());
        response.setPlanType(membershipPlan.getPlanType());
        response.setBillingCycle(membershipPlan.getBillingCycle());
        response.setPrice(membershipPlan.getPrice());
        response.setIsActive(membershipPlan.getIsActive());
        return response;
    }
    
    private Integer calculateProfileCompletion(Account account) {
        int completedFields = 0;
        int totalFields = 6; // name, email, mobile, dob, gender, membershipPlan
        
        if (account.getName() != null && !account.getName().trim().isEmpty()) completedFields++;
        if (account.getEmailAddress() != null && !account.getEmailAddress().trim().isEmpty()) completedFields++;
        if (account.getMobileNumber() != null && !account.getMobileNumber().trim().isEmpty()) completedFields++;
        if (account.getDateOfBirth() != null) completedFields++;
        if (account.getGender() != null) completedFields++;
        if (account.getMembershipPlan() != null) completedFields++;
        
        return (completedFields * 100) / totalFields;
    }
    
    private void updateTransactionCounts(Account account) {
        Integer totalTransactions = accountRepository.countTotalTransactionsByAccountId(account.getId());
        Integer currentMonthTransactions = accountRepository.countCurrentMonthTransactionsByAccountId(account.getId());
        Integer totalBills = accountRepository.countTotalBillsByAccountId(account.getId());
        Integer currentMonthBills = accountRepository.countCurrentMonthBillsByAccountId(account.getId());
        
        account.setTotalTransactions(totalTransactions != null ? totalTransactions : 0);
        account.setCurrentMonthTransactions(currentMonthTransactions != null ? currentMonthTransactions : 0);
        account.setTotalBills(totalBills != null ? totalBills : 0);
        account.setCurrentMonthBills(currentMonthBills != null ? currentMonthBills : 0);
        
        accountRepository.save(account);
    }
}