package com.business.app.rest;

import com.business.app.dto.WithdrawApproveDto;
import com.business.app.service.WithdrawService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для запросов со стороны платежной системы (для вывода средств)
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/acquire")
public class ThirdPartyAcquireRestController {
    @Autowired
    WithdrawService withdrawService;

    @PostMapping("withdraw/{withdraw_id}/approve")
    public ResponseEntity<?> makeWithdraw(@PathVariable(name = "withdraw_id") Long withdrawId,
                                          @RequestBody WithdrawApproveDto withdrawApproveDto,
                                          HttpServletRequest request) {
        log.info("Withdraw approve method called with url: {}", request.getRequestURL());
        return new ResponseEntity<>(withdrawService.approveWithdraw(withdrawApproveDto, request.getRequestURL().toString(),  request.getHeader("Authorization")), HttpStatus.OK);
    }
}
