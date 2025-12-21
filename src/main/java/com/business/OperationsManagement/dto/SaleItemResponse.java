package com.business.OperationsManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemResponse {

    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double itemTotal;
}
