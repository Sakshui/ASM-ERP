package com.business.OperationsManagement.dto;

import com.business.OperationsManagement.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UserRole role; // ADMIN or CUSTOMER
}
