package com.business.app.rest;

import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.PurchaseFromMarketplaceDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotHandledPurchaseException;
import com.business.app.service.MarketplaceService;
import com.business.app.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для запросов со стороны стороннего маркетплейса
 */
@RestController
@RequestMapping(value = "/api/marketplaces")
public class ThirdPartyMarketRestController {

    @Autowired
    private final PurchaseService purchaseService;

    private final MarketplaceService marketplaceService;

    public ThirdPartyMarketRestController(PurchaseService purchaseService, MarketplaceService marketplaceService) {
        this.purchaseService = purchaseService;
        this.marketplaceService = marketplaceService;
    }


    @GetMapping()
    public ResponseEntity<?> getPaginatedMarketplaces(@RequestParam("page") int page,
                                                      @RequestParam("size") int size) {
        return new ResponseEntity<>(marketplaceService.getMarkeplacePage(page, size).getContent(), HttpStatus.OK);
    }

    @PostMapping("purchase")
    public ResponseEntity<?> addPurchase(@RequestBody PurchaseFromMarketplaceDto purchaseFromMarketplaceDto) throws NotFoundRedirectException, NotHandledPurchaseException {
        return new ResponseEntity<>(purchaseService.purchaseAdd(purchaseFromMarketplaceDto), HttpStatus.OK);
    }

    @PostMapping("purchase/{purchase_id}/approve")
    public ResponseEntity<?> approvePurchase(@PathVariable(name = "purchase_id") Long purchaseId,
                                             @RequestBody PurchaseApproveDto purchaseApproveDto) {
        return new ResponseEntity<>(purchaseService.approvePurchase(purchaseId, purchaseApproveDto), HttpStatus.OK);
    }
}
