package com.business.OperationsManagement.dto;

import com.business.OperationsManagement.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private UserRole role;
}
