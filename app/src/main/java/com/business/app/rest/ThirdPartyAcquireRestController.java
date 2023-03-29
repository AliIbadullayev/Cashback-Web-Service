package com.business.app.rest;

import com.example.data.dto.SuccessResponseDto;
import com.example.data.dto.WithdrawApproveDto;
import com.business.app.service.WithdrawService;
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
    public ResponseEntity<?> approveWithdraw(@PathVariable(name = "withdraw_id") String withdrawId,
                               @RequestBody WithdrawApproveDto withdrawApproveDto) {
        log.info("Withdraw approve method called");
        withdrawService.approveWithdraw(withdrawApproveDto,withdrawId);
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setMessage("Заявка успешно отправлена");
        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}
