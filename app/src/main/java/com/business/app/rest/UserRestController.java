package com.business.app.rest;


import com.business.app.dto.RedirectDto;
import com.business.app.exception.RedirectAddException;
import com.business.app.model.Marketplace;
import com.business.app.service.MarketplaceService;
import com.business.app.service.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.business.app.model.User;
import com.business.app.service.PurchaseService;
import com.business.app.service.UserService;
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
    public ResponseEntity<?> addRedirect(@RequestBody RedirectDto redirectDto) throws RedirectAddException {
        return new ResponseEntity<>(redirectService.addRedirect(redirectDto), HttpStatus.OK);
    }


    private final UserService userService;
    private final PurchaseService purchaseService;

    public UserRestController(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "balance/available")
    public ResponseEntity<?> getAvailableBalance(@RequestParam(name = "username") String username){
        User user = userService.findByUsername(username);

        if (user == null) return new ResponseEntity<>("Пользователь с таким именем не найден!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(user.getAvailableBalance(),HttpStatus.OK);
    }


    @GetMapping(value = "balance/pending")
    public ResponseEntity<?> getPendingBalance(@RequestParam(name = "username") String username){
        User user = userService.findByUsername(username);

        if (user == null) return new ResponseEntity<>("Пользователь с таким именем не найден!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(user.getPendingBalance(),HttpStatus.OK);
    }

    @GetMapping(value = "purchases/all")
    public ResponseEntity<?> getAllPurchases(@RequestParam(name = "username") String username){
        User user = userService.findByUsername(username);

        if (user == null) return new ResponseEntity<>("Пользователь с таким именем не найден!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(purchaseService.getAllPurchasesByUser(user),HttpStatus.OK);
    }





}
