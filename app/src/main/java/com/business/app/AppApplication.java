package com.business.app;

import com.business.app.rest.UserRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.example.data", "com.business.app"})
public class AppApplication {

    public static void main(String[] args) {
        ApplicationContext context =  SpringApplication.run(AppApplication.class, args);
//        context.getBean(UserRestController.class);

    }

}
