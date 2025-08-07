package com.dailyspend.controller;

import com.dailyspend.dto.DashboardResponse;
import com.dailyspend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    /**
     * Get dashboard data for current month
     * GET /dashboard/account/{accountId}
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable Long accountId) {
        try {
            DashboardResponse dashboard = dashboardService.getDashboardData(accountId);
            return new ResponseEntity<>(dashboard, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Get dashboard data for specific month
     * GET /dashboard/account/{accountId}/month/{month}
     * 
     * @param accountId The account ID
     * @param month The month in format "MMMM yyyy" (e.g., "August 2025")
     */
    @GetMapping("/account/{accountId}/month/{month}")
    public ResponseEntity<DashboardResponse> getDashboardForMonth(
            @PathVariable Long accountId, 
            @PathVariable String month) {
        try {
            // URL decode the month parameter (handles spaces)
            String decodedMonth = java.net.URLDecoder.decode(month, "UTF-8");
            DashboardResponse dashboard = dashboardService.getDashboardDataForMonth(accountId, decodedMonth);
            return new ResponseEntity<>(dashboard, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get dashboard data for specific month using query parameter
     * GET /dashboard/account/{accountId}?month=August%202025
     * 
     * @param accountId The account ID
     * @param month The month in format "MMMM yyyy" (e.g., "August 2025")
     */
    @GetMapping(value = "/account/{accountId}", params = "month")
    public ResponseEntity<DashboardResponse> getDashboardForMonthQuery(
            @PathVariable Long accountId, 
            @RequestParam String month) {
        try {
            DashboardResponse dashboard = dashboardService.getDashboardDataForMonth(accountId, month);
            return new ResponseEntity<>(dashboard, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}