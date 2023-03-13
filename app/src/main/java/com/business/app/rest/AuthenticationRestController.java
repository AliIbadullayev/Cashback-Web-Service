package com.business.app.rest;


import com.business.app.dto.RegistrationRequestDto;
import com.example.data.model.User;
import com.business.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth/")
public class AuthenticationRestController {

    private final UserService userService;

    public AuthenticationRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequestDto requestDto) {
        return new ResponseEntity<>(userService.register(requestDto.getUsername(), requestDto.getPassword()), HttpStatus.OK);
    }

}
