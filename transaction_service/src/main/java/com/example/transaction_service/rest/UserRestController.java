package com.example.transaction_service.rest;

import com.example.transaction_service.dto.RedirectDto;
import com.example.transaction_service.dto.WithdrawDto;
import com.example.transaction_service.exception.NotFoundRedirectException;
import com.example.transaction_service.exception.NotFoundUserException;
import com.example.transaction_service.service.*;
import com.example.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для запросов со стороны пользователя
 */

@RestController
@RequestMapping(value = "/api/users/")
public class UserRestController {

    @Autowired
    RedirectService redirectService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    UserService userService;

    @Autowired
    WithdrawService withdrawService;


    @PostMapping("redirect")
    public ResponseEntity<?> addRedirect(@RequestBody RedirectDto redirectDto) throws NotFoundRedirectException {
        return new ResponseEntity<>(redirectService.addRedirect(redirectDto), HttpStatus.OK);
    }

    @GetMapping(value = "{username}/available-balance")
    public ResponseEntity<?> getAvailableBalance(@PathVariable(name = "username") String username) throws NotFoundUserException {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getAvailableBalance(), HttpStatus.OK);
    }


    @GetMapping(value = "{username}/pending-balance")
    public ResponseEntity<?> getPendingBalance(@PathVariable(name = "username") String username) throws NotFoundUserException {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user.getPendingBalance(), HttpStatus.OK);
    }

    @GetMapping(value = "{username}/purchases")
    public ResponseEntity<?> getAllPurchases(@PathVariable(name = "username") String username,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size) throws NotFoundUserException {
        User user = userService.getUser(username);

        return new ResponseEntity<>(purchaseService.getPurchasePage(user, page, size), HttpStatus.OK);
    }

    @PostMapping("withdraw")
    public ResponseEntity<?> makeWithdraw(@RequestBody WithdrawDto withdrawDto) {
        return new ResponseEntity<>(withdrawService.sendWithdraw(withdrawDto), HttpStatus.OK);
    }

}
