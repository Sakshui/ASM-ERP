package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.SaleResponse;
import com.business.OperationsManagement.entity.Sale;

public class SaleMapper {

    public static SaleResponse toResponse(Sale sale) {

        SaleResponse res = new SaleResponse();

        res.setId(sale.getId());

        // product details
        res.setProductId(sale.getProduct().getId());
        res.setProductName(sale.getProduct().getName());

        // quantity sold
        res.setQuantitySold(sale.getQuantitySold());

        // sold timestamp
        res.setSoldAt(sale.getSoldAt());

        return res;
    }
}
