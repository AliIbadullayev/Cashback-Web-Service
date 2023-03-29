package com.business.app;

import com.business.app.rest.UserRestController;
import com.business.app.util.IdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication(scanBasePackages = {"com.example.data", "com.business.app"})
public class AppApplication {

    public static void main(String[] args) {
        ApplicationContext context =  SpringApplication.run(AppApplication.class, args);
//        context.getBean(UserRestController.class);


    }

}
