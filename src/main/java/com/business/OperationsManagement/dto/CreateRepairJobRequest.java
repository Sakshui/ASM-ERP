package com.business.OperationsManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateRepairJobRequest {
	
	@NotBlank
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
	private String customerPhone;

	private String customerName;
	
    @NotBlank
    private String machineName;

    @NotBlank
    private String issueDescription;
    
    private Double estimatedPrice;

    private LocalDateTime estimatedReturnDate;
}
