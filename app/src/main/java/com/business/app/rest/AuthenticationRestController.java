package com.business.app.rest;


import com.example.data.dto.AuthenticationRequestDto;
import com.example.data.dto.RefreshTokenRequestDto;
import com.example.data.dto.RegistrationRequestDto;
import com.example.data.model.Actor;
import com.example.data.model.User;
import com.business.app.security.JwtTokenProvider;
import com.business.app.service.ActorService;
import com.example.data.model.XmlActor;
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
        String accessToken = jwtTokenProvider.createAccessToken(request.getUsername().trim(), actor.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(request.getUsername().trim(), actor.getRole().name());

        Map<Object, Object> response = new HashMap<>();
        response.put("username", request.getUsername());
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("role", actor.getRole());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequestDto request) {
        String accessToken = jwtTokenProvider.createAccessTokenByRefreshToken(request.getRefreshToken());

        Map<Object, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", request.getRefreshToken());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("logout")
    public void authenticate(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequestDto requestDto) {
        User user = actorService.register(requestDto.getUsername(), requestDto.getPassword());
        actorService.registerXmlActor(requestDto.getUsername(), requestDto.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("register-xml")
    public ResponseEntity<XmlActor> registerXml(@RequestBody RegistrationRequestDto requestDto) {
        return new ResponseEntity<>(actorService.registerXmlActor(requestDto.getUsername(), requestDto.getPassword()), HttpStatus.OK);
    }


}
