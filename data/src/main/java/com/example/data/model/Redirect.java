package com.example.data.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;


@Entity
@Table(name = "redirects")
@Data
public class Redirect {

    @EmbeddedId
    private RedirectId pk;


    private Timestamp time;


}
