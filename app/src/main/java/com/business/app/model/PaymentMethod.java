package com.business.app.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payment_methods")
@Data
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String credential;

    private double fee;

    private double minAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethodTypes type;

}
