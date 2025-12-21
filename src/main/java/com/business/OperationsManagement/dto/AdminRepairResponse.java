package com.business.OperationsManagement.dto;

import com.business.OperationsManagement.enums.RepairStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminRepairResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String machineName;
    private String issueDescription;
    private Double estimatedPrice;
    private Double finalPrice;
    private String priceNote;


    private RepairStatus status;

    private LocalDateTime acceptedAt;
    private LocalDateTime inProgressAt;
    private LocalDateTime repairedAt;
    private LocalDateTime returnedAt;

    private LocalDateTime estimatedReturnDate;
}
