package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.DashboardSummaryResponse;
import com.business.OperationsManagement.dto.ProductResponse;
import com.business.OperationsManagement.dto.RepairStatsResponse;
import com.business.OperationsManagement.dto.SalesStatsResponse;
import com.business.OperationsManagement.enums.RepairStatus;
import com.business.OperationsManagement.repository.ProductRepository;
import com.business.OperationsManagement.repository.RepairJobRepository;
import com.business.OperationsManagement.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminDashboardService {

    private final RepairJobRepository repairRepo;
    private final ProductRepository productRepo;
    private final SaleRepository saleRepo;
    private final ProductService productService;

    public AdminDashboardService(RepairJobRepository repairRepo,
                                 ProductRepository productRepo,
                                 SaleRepository saleRepo,
                                 ProductService productService) {
        this.repairRepo = repairRepo;
        this.productRepo = productRepo;
        this.saleRepo = saleRepo;
        this.productService = productService;
    }

    // ---------------- DASHBOARD SUMMARY ----------------
    public DashboardSummaryResponse getSummary() {

        DashboardSummaryResponse res = new DashboardSummaryResponse();

        res.setTotalRepairs(repairRepo.count());
        res.setPendingRepairs(repairRepo.countByStatus(RepairStatus.ACCEPTED));
        res.setInProgressRepairs(repairRepo.countByStatus(RepairStatus.IN_PROGRESS));
        res.setRepairedRepairs(repairRepo.countByStatus(RepairStatus.REPAIRED));
        res.setLowStockProducts(productRepo.countLowStockProducts());

        return res;
    }

    // ---------------- REPAIR STATS ----------------
    public RepairStatsResponse getRepairStats() {

        RepairStatsResponse res = new RepairStatsResponse();

        res.setAccepted(repairRepo.countByStatus(RepairStatus.ACCEPTED));
        res.setInProgress(repairRepo.countByStatus(RepairStatus.IN_PROGRESS));
        res.setRepaired(repairRepo.countByStatus(RepairStatus.REPAIRED));
        res.setReturned(repairRepo.countByStatus(RepairStatus.RETURNED));

        return res;
    }

    // ---------------- INVENTORY ALERTS ----------------
    public List<ProductResponse> getInventoryAlerts() {
        return productService.getRestockProducts();
    }

}
