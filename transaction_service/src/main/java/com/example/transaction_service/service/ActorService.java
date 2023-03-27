package com.example.transaction_service.service;


import com.example.data.model.XmlActor;
import com.example.data.repository.XmlActorRepository;
import com.example.transaction_service.exception.NotFoundActorException;
import com.example.transaction_service.exception.NotFoundUserException;
import com.example.data.model.Actor;
import com.example.data.model.User;
import com.example.data.repository.ActorRepository;
import com.example.data.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    private final XmlActorRepository xmlActorRepository;

    private final UserRepository userRepository;


    public ActorService(ActorRepository actorRepository,
                        XmlActorRepository xmlActorRepository,
                        UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.actorRepository = actorRepository;
        this.xmlActorRepository = xmlActorRepository;
        this.userRepository = userRepository;
    }

    public Actor getActor(String username) throws NotFoundUserException {
        Actor actor = actorRepository.findById(username).orElse(null);
        if (actor != null) {
            return actor;
        } else {
            throw new NotFoundActorException("Учетная запись с таким именем не найдена!");
        }
    }

    public XmlActor getXmlActor(String username) throws Exception {
        XmlActor actor = xmlActorRepository.findByUsername(username);
        if (actor != null) {
            return actor;
        } else {
            throw new NotFoundActorException("Учетная запись с таким именем не найдена!");
        }
    }

    public void saveActor(Actor actor) {
        actorRepository.save(actor);
    }

    public void saveXmlActor(XmlActor xmlActor) {
        System.out.println("save xml actor");
        xmlActorRepository.save(xmlActor);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
