package com.dailyspend.controller;

import com.dailyspend.dto.MembershipPlanResponse;
import com.dailyspend.entity.MembershipPlan;
import com.dailyspend.entity.PlanType;
import com.dailyspend.repository.MembershipPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/membership-plans")
@CrossOrigin(origins = "*")
public class MembershipPlanController {
    
    @Autowired
    private MembershipPlanRepository membershipPlanRepository;
    
    @GetMapping
    public ResponseEntity<List<MembershipPlanResponse>> getAllMembershipPlans() {
        List<MembershipPlan> plans = membershipPlanRepository.findAll();
        List<MembershipPlanResponse> responses = plans.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<MembershipPlanResponse>> getActiveMembershipPlans() {
        List<MembershipPlan> plans = membershipPlanRepository.findByIsActiveTrue();
        List<MembershipPlanResponse> responses = plans.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
    @GetMapping("/{planId}")
    public ResponseEntity<MembershipPlanResponse> getMembershipPlanById(@PathVariable Long planId) {
        return membershipPlanRepository.findById(planId)
            .map(plan -> new ResponseEntity<>(mapToResponse(plan), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/type/{planType}")
    public ResponseEntity<List<MembershipPlanResponse>> getMembershipPlansByType(@PathVariable PlanType planType) {
        List<MembershipPlan> plans = membershipPlanRepository.findByPlanTypeAndIsActiveTrue(planType);
        List<MembershipPlanResponse> responses = plans.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
    
    private MembershipPlanResponse mapToResponse(MembershipPlan plan) {
        MembershipPlanResponse response = new MembershipPlanResponse();
        response.setId(plan.getId());
        response.setPlanName(plan.getPlanName());
        response.setDescription(plan.getDescription());
        response.setPlanType(plan.getPlanType());
        response.setBillingCycle(plan.getBillingCycle());
        response.setPrice(plan.getPrice());
        response.setIsActive(plan.getIsActive());
        return response;
    }
}