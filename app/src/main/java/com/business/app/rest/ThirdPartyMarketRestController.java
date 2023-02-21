package com.business.app.rest;

import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.PurchaseFromMarketplaceDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotHandledPurchaseException;
import com.business.app.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Контроллер для запросов со стороны стороннего маркетплейса
 * */
@RestController
@RequestMapping(value = "/api/market/")
public class ThirdPartyMarketRestController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/purchase")
    public ResponseEntity<?> addPurchase(@RequestBody PurchaseFromMarketplaceDto purchaseFromMarketplaceDto) throws NotFoundRedirectException, NotHandledPurchaseException {
        return new ResponseEntity<>(purchaseService.purchaseAdd(purchaseFromMarketplaceDto), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approvePurchase(@RequestBody PurchaseApproveDto purchaseApproveDto) throws NotFoundRedirectException, NotHandledPurchaseException {
        return new ResponseEntity<>(purchaseService.approvePurchase(purchaseApproveDto), HttpStatus.OK);
    }
}
