package com.business.app.model;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlActor {


    public XmlActor(){

    }

    @XmlElement(name = "username")
    private String username;


    @XmlElement(name = "password")
    private String password;


    @XmlElement(name = "role")
    private Role role;

}


