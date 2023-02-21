package com.business.app.service;

import com.business.app.model.User;
import com.business.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUser(String username){
        return userRepository.findById(username).orElse(null);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
