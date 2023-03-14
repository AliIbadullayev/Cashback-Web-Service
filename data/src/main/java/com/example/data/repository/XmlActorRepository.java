//package com.example.data.repository;
//
//
//import com.business.app.model.XmlActor;
//import com.business.app.model.XmlActorsWrapper;
//import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Marshaller;
//import jakarta.xml.bind.Unmarshaller;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Repository;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//@Repository
//public class XmlActorRepository{
//
//    private final Unmarshaller unmarshaller;
//    private final Marshaller marshaller;
//
//    private static final String ACTORS_FILE = "data/actors.xml";
//
//    public XmlActorRepository(Unmarshaller unmarshaller, Marshaller marshaller) {
//        this.unmarshaller = unmarshaller;
//        this.marshaller = marshaller;
//    }
//
//    private File getUsersFile() throws IOException {
//        return new ClassPathResource(ACTORS_FILE).getFile();
//    }
//
//    public XmlActor findByUsername(String username) {
//        List<XmlActor> actors = findAll();
//        return actors.stream()
//                .filter(actor -> actor.getUsername().equals(username))
//                .findFirst()
//                .orElse(null);
//    }
//
//    public void save(XmlActor actor) {
//        List<XmlActor> actors = findAll();
//        actors.add(actor);
//        try {
//            marshaller.marshal(new XmlActorsWrapper(actors), getUsersFile());
//        } catch (IOException | JAXBException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<XmlActor> findAll() {
//        try {
//            XmlActorsWrapper wrapper = (XmlActorsWrapper) unmarshaller.unmarshal(getUsersFile());
//            return wrapper.getActors();
//        } catch (IOException | JAXBException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
