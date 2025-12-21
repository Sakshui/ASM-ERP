package com.business.OperationsManagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateSaleRequest {

    @NotEmpty
    @Valid
    private List<SaleItemRequest> items;
}
