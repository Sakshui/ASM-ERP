package com.business.OperationsManagement.repository;

import com.business.OperationsManagement.entity.Sale;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
}
