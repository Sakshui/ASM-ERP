package com.business.OperationsManagement.dto;

import com.business.OperationsManagement.enums.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private ProductCategory category;
    private int stockQuantity;
    private int restockThreshold;
    private boolean needsRestock;
    private boolean inStock;          // NEW
    private boolean contactAllowed;   // NEW
    private String imageUrl;
    private double unitPrice;
    public String whatsappLink;
    public String description;

    // getters & setters
}
