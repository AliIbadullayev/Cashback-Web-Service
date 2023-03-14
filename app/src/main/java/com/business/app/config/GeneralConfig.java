package com.business.app.config;

import com.business.app.model.XmlActorsWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class GeneralConfig {


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public Marshaller marshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(XmlActorsWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    @Bean
    public Unmarshaller unmarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(XmlActorsWrapper.class);
        return context.createUnmarshaller();
    }
}
