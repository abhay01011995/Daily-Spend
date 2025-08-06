package com.dailyspend.repository;

import com.dailyspend.entity.MembershipPlan;
import com.dailyspend.entity.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
    
    Optional<MembershipPlan> findByPlanName(String planName);
    
    List<MembershipPlan> findByIsActiveTrue();
    
    List<MembershipPlan> findByPlanType(PlanType planType);
    
    List<MembershipPlan> findByPlanTypeAndIsActiveTrue(PlanType planType);
}