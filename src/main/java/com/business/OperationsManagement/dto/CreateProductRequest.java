package com.business.OperationsManagement.dto;

import com.business.OperationsManagement.enums.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    private String name;

    private String description; // optional

    @NotNull
    private ProductCategory category;

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    @NotNull
    @Min(0)
    private Integer restockThreshold;

    // OPTIONAL (validated by category)
    private Double unitPrice;
    private String whatsappLink;
}
