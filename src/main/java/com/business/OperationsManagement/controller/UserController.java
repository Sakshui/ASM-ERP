package com.business.OperationsManagement.controller;

import com.business.OperationsManagement.dto.CreateUserRequest;
import com.business.OperationsManagement.dto.CustomerLookupResponse;
import com.business.OperationsManagement.dto.UpdateProfileRequest;
import com.business.OperationsManagement.dto.UserResponse;
import com.business.OperationsManagement.entity.RepairJob;
import com.business.OperationsManagement.entity.User;
import com.business.OperationsManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // create admin or customer
    @PostMapping
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request) {
        return service.createUser(request);
    }

    // list admins
    @GetMapping("/admins")
    public List<UserResponse> getAdmins() {
        return service.getAdmins();
    }

    // list customers
    @GetMapping("/customers")
    public List<UserResponse> getCustomers() {
        return service.getCustomers();
    }
    
    @GetMapping("/internal/customers/lookup")
    public CustomerLookupResponse lookupCustomer(
            @RequestParam String phone) {

        User user = service.findCustomerIfExists(phone);

        if (user == null) {
            return new CustomerLookupResponse(false, null);
        }

        return new CustomerLookupResponse(true, user.getFullName());
    }
    
    


}
