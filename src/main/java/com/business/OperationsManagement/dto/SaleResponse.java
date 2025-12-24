package com.business.OperationsManagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SaleResponse {

    private Long id;

    private Long productId;
    private String productName;

    private Integer quantitySold;

    private LocalDateTime soldAt;
}
