package com.business.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "marketplaces")
@Data
public class Marketplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rules_id")
    private Rules rules;
    @Column(columnDefinition = "TEXT")
    private String description;

    private String name;


}
