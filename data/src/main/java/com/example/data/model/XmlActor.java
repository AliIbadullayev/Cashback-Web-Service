package com.example.data.model;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actor")
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


