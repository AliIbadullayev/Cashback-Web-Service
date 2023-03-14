package com.business.app.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@XmlEnum
public enum Role {
    @XmlEnumValue("USER")
    USER,
    @XmlEnumValue("MARKET")
    MARKET,
    @XmlEnumValue("ACQUIRE")
    ACQUIRE;

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(this.name()));
        return authorityList;
    }

}
