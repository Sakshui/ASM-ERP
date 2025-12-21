package com.business.OperationsManagement.entity;

import com.business.OperationsManagement.enums.RepairStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.business.OperationsManagement.entity.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;


import java.time.LocalDateTime;

@Entity
@Table(name = "repair_jobs")
@Getter
@Setter
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    
    @Column(nullable = false)
    private String machineName;	

    @Column(nullable = false, length = 500)
    private String issueDescription;
    
    @Column
    private Double estimatedPrice;
    
    private Double finalPrice;

    @Column(length = 500)
    private String priceNote;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepairStatus status;

    private LocalDateTime acceptedAt;
    private LocalDateTime inProgressAt;
    private LocalDateTime repairedAt;
    private LocalDateTime returnedAt;

    private LocalDateTime estimatedReturnDate;

    // auto-set on creation
    @PrePersist
    public void onCreate() {
        this.status = RepairStatus.ACCEPTED;
        this.acceptedAt = LocalDateTime.now();
    }
}
