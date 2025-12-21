package com.business.OperationsManagement.controller;

import com.business.OperationsManagement.dto.DashboardSummaryResponse;
import com.business.OperationsManagement.dto.ProductResponse;
import com.business.OperationsManagement.dto.RepairStatsResponse;
import com.business.OperationsManagement.dto.SalesStatsResponse;
import com.business.OperationsManagement.service.AdminDashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService service;

    public AdminDashboardController(AdminDashboardService service) {
        this.service = service;
    }

    // ---------------- SUMMARY ----------------
    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary() {
        return service.getSummary();
    }

    // ---------------- REPAIR STATS ----------------
    @GetMapping("/repairs")
    public RepairStatsResponse getRepairStats() {
        return service.getRepairStats();
    }

    // ---------------- INVENTORY ALERTS ----------------
    @GetMapping("/inventory-alerts")
    public List<ProductResponse> getInventoryAlerts() {
        return service.getInventoryAlerts();
    }

    // ---------------- SALES STATS ----------------
    @GetMapping("/sales")
    public SalesStatsResponse getSalesStats() {
        return service.getSalesStats();
    }
}
