package com.business.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private String username;

    @JsonIgnore
    private String password;


    private double availableBalance;
    private double pendingBalance;
}
