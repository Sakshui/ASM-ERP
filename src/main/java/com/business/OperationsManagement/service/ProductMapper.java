package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.ProductResponse;
import com.business.OperationsManagement.entity.Product;
import com.business.OperationsManagement.enums.ProductCategory;

import java.util.Set;

public class ProductMapper {

    private static final Set<ProductCategory> CONTACT_ALLOWED_CATEGORIES = Set.of(
            ProductCategory.MOTORS,
            ProductCategory.SEWING_MACHINE_TOP,
            ProductCategory.STAND,
            ProductCategory.TABLE
    );

    public static ProductResponse toResponse(Product product) {

        ProductResponse res = new ProductResponse();

        res.setId(product.getId());
        res.setName(product.getName());
        res.setCategory(product.getCategory());

        res.setStockQuantity(product.getStockQuantity());
        res.setRestockThreshold(product.getRestockThreshold());
        res.setNeedsRestock(product.getNeedsRestock());

        res.setImageUrl(product.getImageUrl());

        // Price (0 for contact-only products)
        res.setUnitPrice(
                product.getUnitPrice() != null ? product.getUnitPrice() : 0.0
        );

        // Stock status (frontend decides label)
        res.setInStock(product.getStockQuantity() > 0);

        // Contact eligibility
        boolean contactAllowed =
                CONTACT_ALLOWED_CATEGORIES.contains(product.getCategory());
        res.setContactAllowed(contactAllowed);

        // ✅ CRITICAL FIX: send WhatsApp link to frontend
        if (contactAllowed) {
            res.setWhatsappLink(product.getWhatsappLink());
        }

        return res;
    }
}
