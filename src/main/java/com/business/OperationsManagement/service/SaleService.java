package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.CreateSaleRequest;
import com.business.OperationsManagement.dto.SaleItemRequest;
import com.business.OperationsManagement.dto.SaleResponse;
import com.business.OperationsManagement.entity.Product;
import com.business.OperationsManagement.entity.Sale;
import com.business.OperationsManagement.entity.SaleItem;
import com.business.OperationsManagement.exception.InvalidStateTransitionException;
import com.business.OperationsManagement.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;

    public SaleService(SaleRepository saleRepository,
                       ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    public SaleResponse createSale(CreateSaleRequest request) {

        Product product = productService.getProductOrThrow(request.getProductId());

        if (request.getQuantitySold() > product.getStockQuantity()) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        // 🔽 reduce stock
        int newStock = product.getStockQuantity() - request.getQuantitySold();
        productService.updateStock(product, newStock);

        // 🧾 save sale record
        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantitySold(request.getQuantitySold());
        sale.setSoldAt(LocalDateTime.now());

        Sale saved = saleRepository.save(sale);

        // response
        SaleResponse res = new SaleResponse();
        res.setId(saved.getId());
        res.setProductId(product.getId());
        res.setProductName(product.getName());
        res.setQuantitySold(saved.getQuantitySold());
        res.setSoldAt(saved.getSoldAt());

        return res;
    }
}
