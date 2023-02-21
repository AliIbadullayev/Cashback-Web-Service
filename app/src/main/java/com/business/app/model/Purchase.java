package com.business.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;


@Entity
@Table(name = "purchases")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marketplace_id")
    private Marketplace marketplace;


    @Enumerated(EnumType.STRING)
    private Status cashbackPaymentStatus;
    private boolean rulesRespected;

    private Timestamp timestamp;

    private double cashbackPercent;

    private double totalPrice;
}
