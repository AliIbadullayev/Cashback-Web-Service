package com.business.app.service;

import com.business.app.model.User;
import com.business.app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
