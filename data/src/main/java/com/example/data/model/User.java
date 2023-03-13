package com.example.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Table(name = "users")
@Data
public class User {




    @Id
    @Column(name = "actor_username")
    private String username;

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private Actor actor;

    private double availableBalance;
    private double pendingBalance;
}
