package com.dailyspend.config;

import com.dailyspend.entity.BillingCycle;
import com.dailyspend.entity.MembershipPlan;
import com.dailyspend.entity.PlanType;
import com.dailyspend.repository.MembershipPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private MembershipPlanRepository membershipPlanRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeMembershipPlans();
    }
    
    private void initializeMembershipPlans() {
        // Check if plans already exist
        if (membershipPlanRepository.count() > 0) {
            return;
        }
        
        // Create Basic Monthly Plan
        MembershipPlan basicMonthly = new MembershipPlan();
        basicMonthly.setPlanName("Basic Monthly Plan");
        basicMonthly.setDescription("Basic features with monthly billing");
        basicMonthly.setPlanType(PlanType.BASIC);
        basicMonthly.setBillingCycle(BillingCycle.MONTHLY);
        basicMonthly.setPrice(new BigDecimal("99.00"));
        basicMonthly.setIsActive(true);
        membershipPlanRepository.save(basicMonthly);
        
        // Create Silver Quarterly Plan
        MembershipPlan silverQuarterly = new MembershipPlan();
        silverQuarterly.setPlanName("Silver Quarterly Plan");
        silverQuarterly.setDescription("Enhanced features with quarterly billing");
        silverQuarterly.setPlanType(PlanType.SILVER);
        silverQuarterly.setBillingCycle(BillingCycle.QUARTERLY);
        silverQuarterly.setPrice(new BigDecimal("249.00"));
        silverQuarterly.setIsActive(true);
        membershipPlanRepository.save(silverQuarterly);
        
        // Create Gold Half Yearly Plan
        MembershipPlan goldHalfYearly = new MembershipPlan();
        goldHalfYearly.setPlanName("Gold Half Yearly Plan");
        goldHalfYearly.setDescription("Premium features with half yearly billing");
        goldHalfYearly.setPlanType(PlanType.GOLD);
        goldHalfYearly.setBillingCycle(BillingCycle.HALF_YEARLY);
        goldHalfYearly.setPrice(new BigDecimal("449.00"));
        goldHalfYearly.setIsActive(true);
        membershipPlanRepository.save(goldHalfYearly);
        
        // Create Platinum Yearly Plan
        MembershipPlan platinumYearly = new MembershipPlan();
        platinumYearly.setPlanName("Platinum Yearly Plan");
        platinumYearly.setDescription("All features with yearly billing");
        platinumYearly.setPlanType(PlanType.PLATINUM);
        platinumYearly.setBillingCycle(BillingCycle.YEARLY);
        platinumYearly.setPrice(new BigDecimal("799.00"));
        platinumYearly.setIsActive(true);
        membershipPlanRepository.save(platinumYearly);
        
        System.out.println("Sample membership plans initialized successfully!");
    }
}