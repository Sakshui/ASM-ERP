package com.business.OperationsManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.business.OperationsManagement.dto.ProductResponse;
import com.business.OperationsManagement.entity.Product;
import com.business.OperationsManagement.enums.ProductCategory;
import com.business.OperationsManagement.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class PublicProductController {

    private final ProductService service;

    public PublicProductController(ProductService service) {
        this.service = service;
    }

    // CUSTOMER: list all available products
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    // CUSTOMER: filter by category (optional)
    @GetMapping("/category")
    public List<ProductResponse> getByCategory(
            @RequestParam ProductCategory category) {
        return service.getByCategory(category);
    }
    
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

}

