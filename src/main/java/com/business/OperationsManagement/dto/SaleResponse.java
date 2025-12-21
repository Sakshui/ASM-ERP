package com.business.OperationsManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SaleResponse {

    private Long saleId;
    private LocalDateTime saleDate;
    private Double totalAmount;
    private List<SaleItemResponse> items;
}
