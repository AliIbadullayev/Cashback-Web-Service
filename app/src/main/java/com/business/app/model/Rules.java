package com.business.app.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name="rules")
@Data
public class Rules implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double minPrice;

    private boolean certificates;

    private boolean promocodes;

}
