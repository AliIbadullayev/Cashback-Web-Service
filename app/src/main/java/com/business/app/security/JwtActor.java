package com.business.app.security;

import com.business.app.model.Actor;
import com.business.app.model.XmlActor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class JwtActor implements UserDetails {

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public JwtActor(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetails fromActor(Actor actor) {
        return new JwtActor(actor.getUsername(), actor.getPassword(), actor.getRole().getAuthorities());
    }

    public static UserDetails fromXmlActor(XmlActor xmlActor) {
        return new JwtActor(xmlActor.getUsername(), xmlActor.getPassword(), xmlActor.getRole().getAuthorities());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
