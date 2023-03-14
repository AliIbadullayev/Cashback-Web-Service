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
@RequestMapping(value = "/api/transaction/users/")
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

    @PostMapping("withdraw")
    public ResponseEntity<?> makeWithdraw(@RequestBody WithdrawDto withdrawDto) {
        return new ResponseEntity<>(withdrawService.sendWithdraw(withdrawDto), HttpStatus.OK);
    }

}
