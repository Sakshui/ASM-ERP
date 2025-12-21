package com.business.OperationsManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
