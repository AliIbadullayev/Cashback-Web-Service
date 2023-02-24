package com.business.app.rest;

import com.business.app.dto.RedirectDto;
import com.business.app.dto.WithdrawDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotFoundUserException;
import com.business.app.service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.business.app.model.User;

/**
 * Контроллер для запросов со стороны пользователя
 */

//TODO: разобраться с рестом и нащваниями маппингов. В маппингах нельзя глаголы исопльзовать
@RestController
@RequestMapping(value = "/api/users/")
@Valid
public class UserRestController {
    @Autowired
    MarketplaceService marketplaceService;

    @Autowired
    RedirectService redirectService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    UserService userService;

    @Autowired
    WithdrawService withdrawService;

    @GetMapping("/marketplaces")
    public ResponseEntity<?> getMarketplaces() {
        return new ResponseEntity<>(marketplaceService.getMarketplaces(), HttpStatus.OK);
    }

    @GetMapping("/markets")
    public ResponseEntity<?> getPaginatedMarketplaces(@RequestParam("page") int page,
                                                      @RequestParam("size") int size) {
        return new ResponseEntity<>(marketplaceService.getMarkeplacePage(page,size).getContent(),HttpStatus.OK);
    }

    @PostMapping("/redirect")
    public ResponseEntity<?> addRedirect(@RequestBody RedirectDto redirectDto) throws NotFoundRedirectException {
        return new ResponseEntity<>(redirectService.addRedirect(redirectDto), HttpStatus.OK);
    }

    @GetMapping(value = "balance/available")
    public ResponseEntity<?> getAvailableBalance(@RequestParam(name = "username") String username) throws NotFoundUserException {
        System.out.println(username);
        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getAvailableBalance(), HttpStatus.OK);
    }


    @GetMapping(value = "balance/pending")
    public ResponseEntity<?> getPendingBalance(@RequestParam(name = "username") String username) throws NotFoundUserException {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getPendingBalance(), HttpStatus.OK);
    }

    @GetMapping(value = "purchases/all")
    public ResponseEntity<?> getAllPurchases(@RequestParam(name = "username") String username) throws NotFoundUserException {
        User user = userService.getUser(username);
        return new ResponseEntity<>(purchaseService.getAllPurchasesByUser(user), HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> makeWithdraw(@RequestBody WithdrawDto withdrawDto) {
        return new ResponseEntity<>(withdrawService.sendWithdraw(withdrawDto), HttpStatus.OK);
    }

}
