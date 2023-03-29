package com.example.transaction_service.jms;

import com.example.data.dto.WithdrawDto;
import com.example.transaction_service.service.*;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

/**
 * Контроллер для запросов со стороны пользователя
 */

@RestController
@RequestMapping(value = "/api/transaction/users/")

public class UserRestController {

    private final WithdrawService withdrawService;

    public UserRestController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }
    @JmsListener(destination = "${withdraw.send.queue}")
    public void sendWithdraw(WithdrawDto withdrawDto) throws SystemException, NotSupportedException {
        withdrawService.sendWithdraw(withdrawDto);
    }

}
