package com.business.OperationsManagement.repository;

import com.business.OperationsManagement.entity.Sale;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	@Query("""
			   SELECT COALESCE(SUM(s.totalAmount), 0)
			   FROM Sale s
			   WHERE DATE(s.saleDate) = :today
			""")
			double getTodayRevenue(LocalDate today);
	
	@Query("""
			   SELECT COALESCE(SUM(s.totalAmount), 0)
			   FROM Sale s
			   WHERE MONTH(s.saleDate) = :month
			     AND YEAR(s.saleDate) = :year
			""")
			double getMonthlyRevenue(int month, int year);


}
