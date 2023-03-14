package com.business.app.service;


import com.business.app.exception.ActorAlreadyExistException;
import com.business.app.exception.NotFoundActorException;
import com.business.app.exception.NotFoundUserException;
import com.example.data.model.Actor;
import com.example.data.model.Role;
import com.example.data.model.User;
//import com.example.data.model.XmlActor;
import com.example.data.repository.ActorRepository;
import com.example.data.repository.MarketplaceRepository;
import com.example.data.repository.UserRepository;
//import com.example.data.repository.XmlActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

    MarketplaceRepository marketplaceRepository;
    private final ActorRepository actorRepository;

//    private final XmlActorRepository xmlActorRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public ActorService(ActorRepository actorRepository,
//                        XmlActorRepository xmlActorRepository,
                        UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.actorRepository = actorRepository;
//        this.xmlActorRepository = xmlActorRepository;
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

//    public XmlActor registerXmlActor(String username, String password){
//        username = username.trim();
//        XmlActor xmlActor = xmlActorRepository.findByUsername(username);
//        if (xmlActor == null) {
//            xmlActor = new XmlActor();
//
//            xmlActor.setUsername(username);
//            xmlActor.setPassword(passwordEncoder.encode(password.trim()));
//            xmlActor.setRole(Role.USER);
//
//
//
//            saveXmlActor(xmlActor);
//
//
//            return xmlActor;
//        } else {
//            throw new ActorAlreadyExistException("Такая учетная запись уже зарегистрирована");
//        }
//    }


    public Actor getActor(String username) throws NotFoundUserException {
        Actor actor = actorRepository.findById(username).orElse(null);
        if (actor != null) {
            return actor;
        } else {
            throw new NotFoundActorException("Учетная запись с таким именем не найдена!");
        }
    }

//    public XmlActor getXmlActor(String username) throws Exception {
//        XmlActor actor = xmlActorRepository.findByUsername(username);
//        if (actor != null) {
//            return actor;
//        } else {
//            throw new NotFoundActorException("Учетная запись с таким именем не найдена!");
//        }
//    }


    public void saveActor(Actor actor) {
        actorRepository.save(actor);
    }

//    public void saveXmlActor(XmlActor xmlActor) {
//        System.out.println("вызов метода save");
//        xmlActorRepository.save(xmlActor);
//    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
