package com.business.OperationsManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFinalPriceRequest {

    @NotNull
    private Double finalPrice;

    // optional
    private String priceNote;
}
