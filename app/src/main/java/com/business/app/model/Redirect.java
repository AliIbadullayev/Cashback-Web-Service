package com.business.app.model;


import jakarta.persistence.*;
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
