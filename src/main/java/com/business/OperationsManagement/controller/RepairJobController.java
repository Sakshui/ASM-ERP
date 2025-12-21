package com.business.OperationsManagement.controller;

import com.business.OperationsManagement.dto.AdminRepairResponse;
import com.business.OperationsManagement.dto.CreateRepairJobRequest;
import com.business.OperationsManagement.dto.CustomerRepairStatusResponse;
import com.business.OperationsManagement.dto.RepairStatsResponse;
import com.business.OperationsManagement.dto.UpdateFinalPriceRequest;
import com.business.OperationsManagement.entity.User;
import com.business.OperationsManagement.enums.RepairStatus;
import com.business.OperationsManagement.service.RepairJobService;
import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repairs")
public class RepairJobController {

    private final RepairJobService service;

    public RepairJobController(RepairJobService service) {
        this.service = service;
    }

    // ADMIN: create repair
    @PostMapping
    public AdminRepairResponse createRepair(
            @Valid @RequestBody CreateRepairJobRequest request) {
        return service.createRepairJob(request);
    }

    // ADMIN: update status
    @PutMapping("/{id}/status")
    public AdminRepairResponse updateStatus(
            @PathVariable Long id,
            @RequestParam RepairStatus status) {
        return service.updateStatus(id, status);
    }
    
    @PutMapping("/{id}/final-price")
    public AdminRepairResponse updateFinalPrice(
            @PathVariable Long id,
            @Valid @RequestBody UpdateFinalPriceRequest request) {

        return service.updateFinalPrice(id, request);
    }
    
    @GetMapping("/customer/{customerId}")
    public List<CustomerRepairStatusResponse> getCustomerRepairs(
            @PathVariable Long customerId) {
        return service.getRepairsByCustomer(customerId);
    }
    
    @GetMapping("/my")
    public List<CustomerRepairStatusResponse> myRepairs() {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return service.getRepairsByCustomer(user.getId());
    }
    
    @GetMapping
    public List<AdminRepairResponse> getAllRepairs() {
        return service.getAllRepairs();
    }
    
 // ADMIN: get repair by id
    @GetMapping("/{id}")
    public AdminRepairResponse getRepairById(@PathVariable Long id) {
        return service.getRepairById(id);
    }




}
