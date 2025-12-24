package com.business.OperationsManagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSaleRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantitySold;
}
