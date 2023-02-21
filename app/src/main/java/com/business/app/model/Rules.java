package com.business.app.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="rules")
@Data
public class Rules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double minPrice;

    private boolean certificates;

    private boolean promocodes;

}
