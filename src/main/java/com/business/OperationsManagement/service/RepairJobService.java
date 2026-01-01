package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.AdminRepairResponse;
import com.business.OperationsManagement.dto.CreateRepairJobRequest;
import com.business.OperationsManagement.dto.CustomerRepairStatusResponse;
import com.business.OperationsManagement.dto.RepairStatsResponse;
import com.business.OperationsManagement.dto.UpdateFinalPriceRequest;
import com.business.OperationsManagement.entity.RepairJob;
import com.business.OperationsManagement.entity.User;
import com.business.OperationsManagement.enums.RepairStatus;
import com.business.OperationsManagement.exception.InvalidStateTransitionException;
import com.business.OperationsManagement.exception.ResourceNotFoundException;
import com.business.OperationsManagement.repository.RepairJobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepairJobService {

    private final RepairJobRepository repository;
    private final UserService userService;


    public RepairJobService(RepairJobRepository repository,
            UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}


    // ADMIN: create repair job
    public AdminRepairResponse createRepairJob(CreateRepairJobRequest request) {

        User customer = userService.getOrCreateCustomerByPhone(
            request.getCustomerPhone(),
            request.getCustomerName()
        );

        RepairJob job = new RepairJob();
        job.setCustomer(customer);
        job.setMachineName(request.getMachineName());
        job.setIssueDescription(request.getIssueDescription());
        job.setEstimatedPrice(request.getEstimatedPrice());
        job.setEstimatedReturnDate(request.getEstimatedReturnDate());

        return RepairJobMapper.toAdminResponse(repository.save(job));
    }




    // ADMIN: update status
    public AdminRepairResponse updateStatus(Long jobId, RepairStatus newStatus) {

        RepairJob job = repository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Repair job not found"));

        validateTransition(job.getStatus(), newStatus);

        // 🔥 store previous state
        job.setPreviousStatus(job.getStatus());
        job.setStatus(newStatus);
        job.setStatusUpdatedAt(LocalDateTime.now());

        updateTimestamps(job, newStatus);

        return RepairJobMapper.toAdminResponse(repository.save(job));
    }

    public AdminRepairResponse undoStatus(Long jobId) {

        RepairJob job = repository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Repair job not found"));

        if (job.getPreviousStatus() == null || job.getStatusUpdatedAt() == null) {
            throw new InvalidStateTransitionException("Undo not available");
        }

        // ⏱ 10 seconds (testing)
        if (job.getStatusUpdatedAt().plusSeconds(10).isBefore(LocalDateTime.now())) {
            throw new InvalidStateTransitionException("Undo window expired");
        }

        RepairStatus currentStatus = job.getStatus();
        RepairStatus previousStatus = job.getPreviousStatus();

        // 🔥 RESET TIMESTAMPS BASED ON CURRENT STATUS
        if (currentStatus == RepairStatus.RETURNED) {
            job.setReturnedAt(null);
        } else if (currentStatus == RepairStatus.REPAIRED) {
            job.setRepairedAt(null);
        } else if (currentStatus == RepairStatus.IN_PROGRESS) {
            job.setInProgressAt(null);
        }

        job.setStatus(previousStatus);
        job.setPreviousStatus(null);
        job.setStatusUpdatedAt(null);

        return RepairJobMapper.toAdminResponse(repository.save(job));
    }



    // CUSTOMER: query status
    public List<CustomerRepairStatusResponse> getRepairsByCustomer(Long customerId) {

        User customer = userService.getCustomerById(customerId);

        return repository.findByCustomer(customer)
                .stream()
                .map(RepairJobMapper::toCustomerResponse)
                .toList();
    }

    // ---------- helpers ----------

    private void validateTransition(RepairStatus current, RepairStatus next) {

        if (current == RepairStatus.ACCEPTED && next != RepairStatus.IN_PROGRESS)
            throw new InvalidStateTransitionException(
                    "Only IN_PROGRESS is allowed after ACCEPTED");

        if (current == RepairStatus.IN_PROGRESS && next != RepairStatus.REPAIRED)
            throw new InvalidStateTransitionException(
                    "Only REPAIRED is allowed after IN_PROGRESS");

        if (current == RepairStatus.REPAIRED && next != RepairStatus.RETURNED)
            throw new InvalidStateTransitionException(
                    "Only RETURNED is allowed after REPAIRED");
    }

    private void updateTimestamps(RepairJob job, RepairStatus status) {

        LocalDateTime now = LocalDateTime.now();

        switch (status) {
            case IN_PROGRESS -> job.setInProgressAt(now);
            case REPAIRED -> job.setRepairedAt(now);
            case RETURNED -> job.setReturnedAt(now);
        }
    }
    
    public AdminRepairResponse updateFinalPrice(
            Long jobId,
            UpdateFinalPriceRequest request) {

        RepairJob job = repository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Repair job not found"));

        // business rule: final price only after repair
        if (job.getStatus() != RepairStatus.REPAIRED
                && job.getStatus() != RepairStatus.RETURNED) {
            throw new InvalidStateTransitionException(
                    "Final price can be updated only after repair is completed");
        }

        job.setFinalPrice(request.getFinalPrice());
        job.setPriceNote(request.getPriceNote());

        RepairJob updated = repository.save(job);
        return RepairJobMapper.toAdminResponse(updated);
    }
    
    public List<AdminRepairResponse> getAllRepairs() {
        return repository.findAll()
                .stream()
                .map(RepairJobMapper::toAdminResponse)
                .toList();
    }
    
    public AdminRepairResponse getRepairById(Long id) {

        RepairJob job = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repair job not found"));

        return RepairJobMapper.toAdminResponse(job);
    }
    
    public List<CustomerRepairStatusResponse> getRepairsByPhone(String phone) {

        return repository.findByCustomer_Phone(phone)
                .stream()
                .map(RepairJobMapper::toCustomerResponse)
                .toList();
    }

}
