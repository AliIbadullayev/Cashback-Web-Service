package com.example.transaction_service.jms;

import com.example.data.dto.WithdrawApproveDto;
import com.example.transaction_service.service.WithdrawService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

/**
 * Контроллер для запросов со стороны платежной системы (для вывода средств)
 */
@RestController
@RequestMapping(value = "/api/transaction/acquire")
public class ThirdPartyAcquireRestController {
    private final WithdrawService withdrawService;


    public ThirdPartyAcquireRestController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @JmsListener(destination = "${withdraw.approve.queue}")
    public void approveWithdraw(WithdrawApproveDto withdrawApproveDto) throws SystemException, NotSupportedException {
        withdrawService.approveWithdraw(withdrawApproveDto);
    }
}
