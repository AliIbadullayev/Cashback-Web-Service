package com.example.transaction_service.service;

import com.example.transaction_service.exception.NotFoundUserException;
import com.example.transaction_service.exception.UserAlreadyExistException;
import com.example.data.model.User;
import com.example.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public User register(String username, String password) throws NotFoundUserException {
        username = username.trim();
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            saveUser(user);
            return user;
        } else {
            throw new UserAlreadyExistException("Такой пользователь уже зарегистрирован");
        }
    }

    public User getUser(String username) throws NotFoundUserException {
        User user = userRepository.findById(username).orElse(null);
        if (user != null) {
            return user;
        } else {
            throw new NotFoundUserException("Пользователь с таким именем не найден!");
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
