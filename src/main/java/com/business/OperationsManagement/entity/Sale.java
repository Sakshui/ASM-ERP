package com.business.OperationsManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // what was sold
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // how many units sold
    @Column(nullable = false)
    private Integer quantitySold;

    // when it was sold
    @Column(nullable = false)
    private LocalDateTime soldAt;
}
