package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.CreateUserRequest;
import com.business.OperationsManagement.dto.UserResponse;
import com.business.OperationsManagement.entity.User;
import com.business.OperationsManagement.enums.UserRole;
import com.business.OperationsManagement.exception.InvalidStateTransitionException;
import com.business.OperationsManagement.exception.ResourceNotFoundException;
import com.business.OperationsManagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // ADMIN: create user
    public UserResponse createUser(CreateUserRequest request) {

        User user = new User();
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        User saved = repository.save(user);
        return UserMapper.toResponse(saved);
    }

    // ADMIN: list all admins
    public List<UserResponse> getAdmins() {
        return repository.findByRole(UserRole.ADMIN)
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    // ADMIN: list all customers
    public List<UserResponse> getCustomers() {
        return repository.findByRole(UserRole.CUSTOMER)
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    // INTERNAL: get customer by phone
    public User getCustomerByPhone(String phone) {
        return repository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public User getCustomerById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != UserRole.CUSTOMER) {
            throw new InvalidStateTransitionException("User is not a customer");
        }
        return user;
    }
    
    public User getOrCreateCustomerByPhone(String phone, String name) {

        return repository.findByPhone(phone)
            .orElseGet(() -> {
                User user = new User();
                user.setPhone(phone);
                user.setFullName(name);     // ✅ correct setter
                user.setRole(UserRole.CUSTOMER);
                user.setIsActivated(false); // cannot login yet
                user.setPassword(null);
                user.setEmail(null);        // ✅ now allowed
                return repository.save(user);
            });
    }

    
    public User findCustomerIfExists(String phone) {
        return repository.findByPhone(phone).orElse(null);
    }



}
