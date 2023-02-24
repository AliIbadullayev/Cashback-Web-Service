package com.business.app.rest;

import com.business.app.dto.WithdrawApproveDto;
import com.business.app.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для запросов со стороны платежной системы (для вывода средств)
 * */
@RestController
@RequestMapping(value = "/api/acquire")
public class ThirdPartyAcquireRestController {
    @Autowired
    WithdrawService withdrawService;

    @PostMapping("approve-withdraw")
    public ResponseEntity<?> makeWithdraw(@RequestBody WithdrawApproveDto withdrawApproveDto)  {
        return new ResponseEntity<>(withdrawService.approveWithdraw(withdrawApproveDto), HttpStatus.OK);
    }
}
