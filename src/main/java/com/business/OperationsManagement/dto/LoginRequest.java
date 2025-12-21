package com.business.OperationsManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String identifier;
    private String password;
}
