package com.business.OperationsManagement.repository;

import com.business.OperationsManagement.dto.CustomerRepairStatusResponse;
import com.business.OperationsManagement.entity.RepairJob;
import com.business.OperationsManagement.entity.User;
import com.business.OperationsManagement.enums.RepairStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepairJobRepository extends JpaRepository<RepairJob, Long> {

	List<RepairJob> findByCustomer(User customer);
	
	long countByStatus(RepairStatus status);
	
    List<RepairJob> findByCustomer_Phone(String phone);

}
