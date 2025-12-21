package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.UserResponse;
import com.business.OperationsManagement.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {

        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setFullName(user.getFullName());
        res.setPhone(user.getPhone());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole());

        return res;
    }
}
