package com.business.OperationsManagement.entity;

import com.business.OperationsManagement.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = true, unique = true)
    private String email;

    // password will be used later
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private Boolean isActivated = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}
