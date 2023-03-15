package com.example.data.repository;


import com.example.data.model.XmlActor;
import com.example.data.model.XmlActorsWrapper;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Repository
public class XmlActorRepository{

    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;

    private static final String ACTORS_FILE = "data/actors.xml";

//    private static final File file = new File(ACTORS_FILE);

    @Autowired
    private ApplicationContext context;

    public XmlActorRepository(Unmarshaller unmarshaller, Marshaller marshaller) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
    }

    private File getUsersFile() throws IOException, URISyntaxException {
        return new File(ACTORS_FILE);
    }

    public XmlActor findByUsername(String username) {
        List<XmlActor> actors = findAll();
        return actors.stream()
                .filter(actor -> actor.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void save(XmlActor actor) {
        List<XmlActor> actors = findAll();
        actors.add(actor);
        try {
            System.out.println("Маршализация "+getUsersFile());
            marshaller.marshal(new XmlActorsWrapper(actors), getUsersFile());
            System.out.println("После маршализации ");
        } catch (JAXBException | IOException | URISyntaxException e) {
            throw new RuntimeException("Файл с данными учетных записей не найден!");
        }
    }

    public List<XmlActor> findAll() {
        try {
            XmlActorsWrapper wrapper = (XmlActorsWrapper) unmarshaller.unmarshal(getUsersFile());
            return wrapper.getActors();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException("Файл с данными учетных записей не найден!");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
