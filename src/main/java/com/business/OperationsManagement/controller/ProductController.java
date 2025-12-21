package com.business.OperationsManagement.controller;

import com.business.OperationsManagement.dto.CreateProductRequest;
import com.business.OperationsManagement.dto.ProductResponse;
import com.business.OperationsManagement.enums.ProductCategory;
import com.business.OperationsManagement.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // create product
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse createProduct(
            @RequestPart("data") @Valid CreateProductRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return service.createProduct(request, image);
    }


    // list all
    @GetMapping
    public List<ProductResponse> getAll() {
        return service.getAllProducts();
    }

    // filter by category
    @GetMapping("/category")
    public List<ProductResponse> getByCategory(
            @RequestParam ProductCategory category) {
        return service.getByCategory(category);
    }

    // restock alerts
    @GetMapping("/restock")
    public List<ProductResponse> restockAlerts() {
        return service.getRestockProducts();
    }

}
