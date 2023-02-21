package com.business.app.rest;


import com.business.app.dto.RedirectDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.service.MarketplaceService;
import com.business.app.service.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для запросов со стороны пользователя
 * */
@RestController
@RequestMapping(value = "/api/users/")
public class UserRestController {
    @Autowired
    MarketplaceService marketplaceService;

    @Autowired
    RedirectService redirectService;

    @GetMapping("/marketplaces")
    public ResponseEntity<?> getMarketplaces(){
        return new ResponseEntity<>(marketplaceService.getMarketplaces(), HttpStatus.OK);
    }

    @PostMapping("/redirect")
    public ResponseEntity<?> addRedirect(@RequestBody RedirectDto redirectDto) throws NotFoundRedirectException {
        return new ResponseEntity<>(redirectService.addRedirect(redirectDto), HttpStatus.OK);
    }

}
