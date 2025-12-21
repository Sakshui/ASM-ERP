package com.business.OperationsManagement.entity;

import com.business.OperationsManagement.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = true, length = 1000)
    private String description;
    
    @Column(name = "image_path")
    private String imageUrl;
    
    @Column(nullable = false)
    private Double unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Integer restockThreshold;

    // derived / computed flag
    private Boolean needsRestock;
    
    @Column(name = "whatsapp_link")
    private String whatsappLink;
}
