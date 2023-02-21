package com.business.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectId implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private User user;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marketplace_id")
    private Marketplace marketplace;


}
