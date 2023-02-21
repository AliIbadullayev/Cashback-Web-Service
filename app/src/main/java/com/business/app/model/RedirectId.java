package com.business.app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Embeddable
@Data
public class RedirectId implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marketplace_id")
    private Marketplace marketplace;


}
