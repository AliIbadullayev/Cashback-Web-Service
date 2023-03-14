package com.example.transaction_service.security;

import com.example.transaction_service.service.ActorService;
import com.example.data.model.Actor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private final ActorService actorService;


    public JwtUserDetailsService(ActorService actorService) {
        this.actorService = actorService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Actor actor = actorService.getActor(username);
//        try {
//            XmlActor xmlActor = actorService.getXmlActor(username);
//            return JwtActor.fromXmlActor(xmlActor);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return JwtActor.fromActor(actor);
    }
}
