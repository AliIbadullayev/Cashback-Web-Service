package com.example.transaction_service.rest;

import com.example.transaction_service.dto.WithdrawApproveDto;
import com.example.transaction_service.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для запросов со стороны платежной системы (для вывода средств)
 */
@RestController
@RequestMapping(value = "/api/transactions/acquire")
public class ThirdPartyAcquireRestController {
    @Autowired
    WithdrawService withdrawService;

    @PostMapping("withdraw/{withdraw_id}/approve")
    public ResponseEntity<?> makeWithdraw(@PathVariable(name = "withdraw_id") Long withdrawId,
                                          @RequestBody WithdrawApproveDto withdrawApproveDto) {
        return new ResponseEntity<>(withdrawService.approveWithdraw(withdrawId, withdrawApproveDto), HttpStatus.OK);
    }
}
