package com.business.OperationsManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerLookupResponse {
    private boolean exists;
    private String name;

    public CustomerLookupResponse(boolean exists, String name) {
        this.exists = exists;
        this.name = name;
    }

    // getters & setters
}
