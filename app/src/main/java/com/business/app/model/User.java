package com.business.app.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private String username;

    private String password;

    private double availableBalance;
    private double pendingBalance;




}
