package com.business.app.rest;


import com.business.app.dto.AuthenticationRequestDto;
import com.business.app.dto.RegistrationRequestDto;
import com.business.app.model.Actor;
import com.business.app.model.User;
import com.business.app.security.JwtTokenProvider;
import com.business.app.service.ActorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth/")
public class AuthenticationRestController {


    private final ActorService actorService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;


    public AuthenticationRestController(ActorService actorService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.actorService = actorService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword()));
        Actor actor = actorService.getActor(request.getUsername().trim());
        String token = jwtTokenProvider.createToken(request.getUsername().trim(), actor.getRole().name());

        Map<Object, Object> response = new HashMap<>();
        response.put("username", request.getUsername());
        response.put("token", token);
        response.put("role", actor.getRole());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("logout")
    public void authenticate(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequestDto requestDto) {
        return new ResponseEntity<>(actorService.register(requestDto.getUsername(), requestDto.getPassword()), HttpStatus.OK);
    }

}
