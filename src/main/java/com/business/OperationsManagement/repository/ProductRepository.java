package com.business.OperationsManagement.repository;

import com.business.OperationsManagement.entity.Product;
import com.business.OperationsManagement.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByNeedsRestockTrue();
    
    long countByNeedsRestockTrue();
    
 // ✅ ONLY LOW STOCK (exclude out of stock)
    @Query("""
        SELECT COUNT(p)
        FROM Product p
        WHERE p.stockQuantity > 0
        AND p.stockQuantity <= p.restockThreshold
    """)
    long countLowStockProducts();

    // (Optional, useful later)
    long countByStockQuantity(int stockQuantity);

}
