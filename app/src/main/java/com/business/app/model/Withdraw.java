package com.business.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "withdraw_types")
@Data
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status withdrawStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private Timestamp time;

    private double amount;


}
