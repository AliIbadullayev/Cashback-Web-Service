package com.example.transaction_service.rest;

import com.example.data.dto.PurchaseApproveDto;
import com.example.data.dto.PurchaseFromMarketplaceDto;
import com.example.transaction_service.exception.NotFoundRedirectException;
import com.example.transaction_service.exception.NotHandledPurchaseException;
import com.example.transaction_service.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;


/**
 * Контроллер для запросов со стороны стороннего маркетплейса
 */
@RestController
@RequestMapping(value = "/api/transaction/marketplaces")
public class ThirdPartyMarketRestController {

    @Autowired
    private final PurchaseService purchaseService;

    private ObjectMapper mapper;

    @PostConstruct
    public void onInit(){
        mapper = new ObjectMapper();
    }
    public ThirdPartyMarketRestController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    @JmsListener(destination = "purchase_add")
    public void addPurchase(String msg) throws NotFoundRedirectException, NotHandledPurchaseException, SystemException, JsonProcessingException {
        PurchaseFromMarketplaceDto purchaseFromMarketplaceDto = mapper.readValue(msg, PurchaseFromMarketplaceDto.class);
        purchaseService.purchaseAdd(purchaseFromMarketplaceDto);
    }

    @JmsListener(destination = "purchase_approve")
    public void approvePurchase(String msg) throws SystemException, JsonProcessingException {
        PurchaseApproveDto purchaseApproveDto = mapper.readValue(msg, PurchaseApproveDto.class);
        purchaseService.approvePurchase(purchaseApproveDto);
    }
}
