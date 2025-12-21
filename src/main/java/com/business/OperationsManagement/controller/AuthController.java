package com.business.OperationsManagement.controller;

import com.business.OperationsManagement.dto.LoginRequest;
import com.business.OperationsManagement.dto.LoginResponse;
import com.business.OperationsManagement.dto.SignupRequest;
import com.business.OperationsManagement.dto.SignupResponse;
import com.business.OperationsManagement.entity.User;
import com.business.OperationsManagement.enums.UserRole;
import com.business.OperationsManagement.repository.UserRepository;
import com.business.OperationsManagement.security.JwtUtil;
import com.business.OperationsManagement.security.TokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenBlacklist tokenBlacklist;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          TokenBlacklist tokenBlacklist) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.tokenBlacklist = tokenBlacklist;
    }
    
    @PostMapping("/signup")
    public SignupResponse signup(@RequestBody SignupRequest request) {

        User user = userRepository.findByPhone(request.getPhone())
                .orElse(null);

        // Case 1: User exists but was created by admin
        if (user != null) {

            if (Boolean.TRUE.equals(user.getIsActivated())) {
                throw new RuntimeException("User already registered");
            }

            // 🔥 ACTIVATE ACCOUNT
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setIsActivated(true);

            userRepository.save(user);

        } 
        // Case 2: Brand new user
        else {
            User newUser = new User();
            newUser.setFullName(request.getFullName());
            newUser.setPhone(request.getPhone());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser.setRole(UserRole.CUSTOMER);
            newUser.setIsActivated(true);

            userRepository.save(newUser);
        }

        SignupResponse response = new SignupResponse();
        response.setMessage("Signup successful. Please login.");
        return response;
    }




    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String identifier = request.getIdentifier();

        User user = userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByPhone(identifier))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // 🚨 IMPORTANT: block inactive users
        if (!Boolean.TRUE.equals(user.getIsActivated())) {
            throw new RuntimeException("Account not activated. Please sign up.");
        }

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setRole(user.getRole().name());

        return response;
    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            tokenBlacklist.blacklist(header.substring(7));
        }
    }
}
