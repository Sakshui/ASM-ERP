package com.business.OperationsManagement.dto;

import com.business.OperationsManagement.enums.RepairStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRepairStatusResponse {

	private String customerName;
	private String machineName;
	private Long repairId;
    private RepairStatus status;
    private LocalDateTime estimatedReturnDate;
    private Double estimatedPrice;
    private Double finalPrice;
}
