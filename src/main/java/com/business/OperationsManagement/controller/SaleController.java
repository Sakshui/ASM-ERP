package com.business.OperationsManagement.controller;

import com.business.OperationsManagement.dto.CreateSaleRequest;
import com.business.OperationsManagement.dto.SaleResponse;
import com.business.OperationsManagement.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/sales")
public class SaleController {

    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @PostMapping
    public SaleResponse createSale(
            @RequestBody @Valid CreateSaleRequest request
    ) {
        return service.createSale(request);
    }
}
