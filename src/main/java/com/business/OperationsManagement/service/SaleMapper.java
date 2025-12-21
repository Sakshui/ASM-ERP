package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.SaleItemResponse;
import com.business.OperationsManagement.dto.SaleResponse;
import com.business.OperationsManagement.entity.Sale;

import java.util.stream.Collectors;

public class SaleMapper {

    public static SaleResponse toResponse(Sale sale) {

        SaleResponse res = new SaleResponse();
        res.setSaleId(sale.getId());
        res.setSaleDate(sale.getSaleDate());
        res.setTotalAmount(sale.getTotalAmount());

        res.setItems(
                sale.getItems().stream().map(item -> {
                    SaleItemResponse r = new SaleItemResponse();
                    r.setProductName(item.getProduct().getName());
                    r.setQuantity(item.getQuantity());
                    r.setUnitPrice(item.getUnitPrice());
                    r.setItemTotal(item.getItemTotal());
                    return r;
                }).collect(Collectors.toList())
        );

        return res;
    }
}
