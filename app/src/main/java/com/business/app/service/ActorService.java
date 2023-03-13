package com.business.app.service;


import com.business.app.exception.ActorAlreadyExistException;
import com.business.app.exception.NotFoundActorException;
import com.business.app.exception.NotFoundUserException;
import com.business.app.model.Actor;
import com.business.app.model.Role;
import com.business.app.model.User;
import com.business.app.repository.ActorRepository;
import com.business.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ActorService {


    private final ActorRepository actorRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public ActorService(ActorRepository actorRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.actorRepository = actorRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User register(String username, String password) throws NotFoundUserException {
        username = username.trim();
        Actor actor = actorRepository.findById(username).orElse(null);
        if (actor == null) {
            actor = new Actor();
            User user = new User();

            actor.setUsername(username);
            actor.setPassword(passwordEncoder.encode(password.trim()));
            actor.setRole(Role.USER);

            user.setUsername(username);

            saveActor(actor);
            saveUser(user);

            return user;
        } else {
            throw new ActorAlreadyExistException("Такая учетная запись уже зарегистрирована");
        }
    }


    public Actor getActor(String username) throws NotFoundUserException {
        Actor actor = actorRepository.findById(username).orElse(null);
        if (actor != null) {
            return actor;
        } else {
            throw new NotFoundActorException("Учетная запись с таким именем не найдена!");
        }
    }


    public void saveActor(Actor actor) {
        actorRepository.save(actor);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
