package com.business.app.rest;

import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.PurchaseFromMarketplaceDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotHandledPurchaseException;
import com.business.app.service.MarketplaceService;
import com.business.app.service.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для запросов со стороны стороннего маркетплейса
 */
@Slf4j
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
    public ResponseEntity<?> addPurchase(@RequestBody PurchaseFromMarketplaceDto purchaseFromMarketplaceDto,
                                         HttpServletRequest request)  throws NotFoundRedirectException, NotHandledPurchaseException {
        log.info("Purchase method called with url: {}", request.getRequestURL());
        return new ResponseEntity<>(purchaseService.purchaseAdd(purchaseFromMarketplaceDto, request.getRequestURL().toString()), HttpStatus.OK);
    }

    @PostMapping("purchase/{purchase_id}/approve")
    public ResponseEntity<?> approvePurchase(@PathVariable(name = "purchase_id") Long purchaseId,
                                             @RequestBody PurchaseApproveDto purchaseApproveDto,
                                             HttpServletRequest request) {
        log.info("Purchase approve method called with url: {}", request.getRequestURL());
        return new ResponseEntity<>(purchaseService.approvePurchase(purchaseApproveDto, request.getRequestURL().toString()), HttpStatus.OK);
    }
}
