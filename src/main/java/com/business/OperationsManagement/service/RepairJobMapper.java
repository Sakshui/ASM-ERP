package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.AdminRepairResponse;
import com.business.OperationsManagement.dto.CustomerRepairStatusResponse;
import com.business.OperationsManagement.entity.RepairJob;

public class RepairJobMapper {

	public static AdminRepairResponse toAdminResponse(RepairJob job) {
	    AdminRepairResponse res = new AdminRepairResponse();

	    res.setId(job.getId());
	    res.setCustomerId(job.getCustomer().getId());
	    res.setCustomerName(job.getCustomer().getFullName());
	    res.setCustomerPhone(job.getCustomer().getPhone());
	    res.setMachineName(job.getMachineName());
	    res.setIssueDescription(job.getIssueDescription());
	    res.setEstimatedPrice(job.getEstimatedPrice());
	    res.setStatus(job.getStatus());
	    res.setFinalPrice(job.getFinalPrice());
	    res.setPriceNote(job.getPriceNote());

	    res.setAcceptedAt(job.getAcceptedAt());
	    res.setInProgressAt(job.getInProgressAt());
	    res.setRepairedAt(job.getRepairedAt());
	    res.setReturnedAt(job.getReturnedAt());
	    res.setEstimatedReturnDate(job.getEstimatedReturnDate());

	    return res;
	}

	public static CustomerRepairStatusResponse toCustomerResponse(RepairJob job) {
	    CustomerRepairStatusResponse res = new CustomerRepairStatusResponse();

	    res.setMachineName(job.getMachineName());
	    res.setRepairId(job.getId());
	    res.setStatus(job.getStatus());
	    res.setEstimatedReturnDate(job.getEstimatedReturnDate());
	    res.setEstimatedPrice(job.getEstimatedPrice());
	    res.setFinalPrice(job.getFinalPrice());
	    res.setCustomerName(job.getCustomer().getFullName());
	    res.setPriceNote(job.getPriceNote());

	    return res;
	}

}
