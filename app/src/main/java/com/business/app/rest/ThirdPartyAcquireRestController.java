package com.business.app.rest;

import com.example.data.dto.WithdrawApproveDto;
import com.business.app.service.WithdrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String makeWithdraw(@PathVariable(name = "withdraw_id") String withdrawId,
                               @RequestBody WithdrawApproveDto withdrawApproveDto) {
        log.info("Withdraw approve method called");
        withdrawService.approveWithdraw(withdrawApproveDto,withdrawId);
        return "Заявка на подтверждения вывода отправлена";
    }
}
