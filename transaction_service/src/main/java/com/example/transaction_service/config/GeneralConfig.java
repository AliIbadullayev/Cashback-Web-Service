package com.example.transaction_service.config;


import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import com.example.transaction_service.advice.AppExceptionHandler;
import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Slf4j
@Configuration
public class GeneralConfig {
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory, AppExceptionHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean(destroyMethod = "shutdown")
    public BitronixTransactionManager bitronixTransactionManager() {
        return TransactionManagerServices.getTransactionManager();
    }

}
