package com.business.app.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionServiceRequestHandler {
    @Value("${transaction.service.port}")
    private Long TRANSACTION_PORT;

    @Value("${server.port}")
    private Long SERVER_PORT;
    public String generateUrlForTransactionService(String url){
        String newUrl = url.replaceFirst("api/", "api/transaction/");
        newUrl = newUrl.replaceFirst(":"+SERVER_PORT, ":"+TRANSACTION_PORT);
        log.info("Request will be sent to transaction service on URL: {}", newUrl);
        return newUrl;
    }
}
