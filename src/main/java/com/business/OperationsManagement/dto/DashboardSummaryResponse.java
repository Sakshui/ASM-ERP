package com.business.OperationsManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardSummaryResponse {

    private long totalRepairs;
    private long pendingRepairs;
    private long inProgressRepairs;
    private long lowStockProducts;
    private double todayRevenue;
    private long repairedRepairs;
}
