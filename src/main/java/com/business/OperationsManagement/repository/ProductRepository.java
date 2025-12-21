package com.business.OperationsManagement.repository;

import com.business.OperationsManagement.entity.Product;
import com.business.OperationsManagement.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByNeedsRestockTrue();
    
    long countByNeedsRestockTrue();

}
