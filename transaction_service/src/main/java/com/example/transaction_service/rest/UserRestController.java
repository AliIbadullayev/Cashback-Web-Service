package com.example.transaction_service.rest;

import com.example.data.dto.WithdrawDto;
import com.example.transaction_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

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

    @PostMapping("withdraw")
    public ResponseEntity<?> makeWithdraw(@RequestBody WithdrawDto withdrawDto) throws SystemException, NotSupportedException {
        return new ResponseEntity<>(withdrawService.sendWithdraw(withdrawDto), HttpStatus.OK);
    }

}
