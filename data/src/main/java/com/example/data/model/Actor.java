package com.example.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "actors")
@Data
public class Actor {

    @Id
    private String username;


    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


}
