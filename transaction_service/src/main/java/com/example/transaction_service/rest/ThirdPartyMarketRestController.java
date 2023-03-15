package com.example.transaction_service.rest;

import com.example.transaction_service.dto.PurchaseApproveDto;
import com.example.transaction_service.dto.PurchaseFromMarketplaceDto;
import com.example.transaction_service.exception.NotFoundRedirectException;
import com.example.transaction_service.exception.NotHandledPurchaseException;
import com.example.transaction_service.service.MarketplaceService;
import com.example.transaction_service.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final MarketplaceService marketplaceService;

    public ThirdPartyMarketRestController(PurchaseService purchaseService, MarketplaceService marketplaceService) {
        this.purchaseService = purchaseService;
        this.marketplaceService = marketplaceService;
    }

    @PostMapping("purchase")
    public ResponseEntity<?> addPurchase(@RequestBody PurchaseFromMarketplaceDto purchaseFromMarketplaceDto) throws NotFoundRedirectException, NotHandledPurchaseException, SystemException, NotSupportedException {
        return new ResponseEntity<>(purchaseService.purchaseAdd(purchaseFromMarketplaceDto), HttpStatus.OK);
    }

    @PostMapping("purchase/{purchase_id}/approve")
    public ResponseEntity<?> approvePurchase(@PathVariable(name = "purchase_id") Long purchaseId,
                                             @RequestBody PurchaseApproveDto purchaseApproveDto) throws SystemException, NotSupportedException {
        return new ResponseEntity<>(purchaseService.approvePurchase(purchaseId, purchaseApproveDto), HttpStatus.OK);
    }
}
