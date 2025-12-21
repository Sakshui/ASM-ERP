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

    @Transactional
    public SaleResponse createSale(CreateSaleRequest request) {

        Sale sale = new Sale();
        List<SaleItem> saleItems = new ArrayList<>();

        double totalAmount = 0;

        // 1️⃣ Validate & prepare items
        for (SaleItemRequest itemReq : request.getItems()) {

            Product product = productService.getProductOrThrow(itemReq.getProductId());

            if (product.getStockQuantity() < itemReq.getQuantity()) {
                throw new InvalidStateTransitionException(
                        "Insufficient stock for product: " + product.getName());
            }

            // deduct stock
            int newStock = product.getStockQuantity() - itemReq.getQuantity();
            productService.updateStock(product, newStock);

            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getUnitPrice());

            double itemTotal = product.getUnitPrice() * itemReq.getQuantity();
            item.setItemTotal(itemTotal);

            totalAmount += itemTotal;
            saleItems.add(item);
        }

        // 2️⃣ Finalize sale
        sale.setItems(saleItems);
        sale.setTotalAmount(totalAmount);

        Sale saved = saleRepository.save(sale);
        return SaleMapper.toResponse(saved);
    }
}
