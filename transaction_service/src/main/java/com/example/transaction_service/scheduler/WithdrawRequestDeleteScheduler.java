package com.example.transaction_service.scheduler;


import com.example.data.repository.WithdrawRepository;
import com.example.transaction_service.service.WithdrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Component
public class WithdrawRequestDeleteScheduler {

    private  final WithdrawService withdrawService;

    private final WithdrawRepository withdrawRepository;

    private final long ONE_HOUR_IN_MILLIS = 3600000;


    public WithdrawRequestDeleteScheduler(WithdrawService withdrawService, WithdrawRepository withdrawRepository) {
        this.withdrawService = withdrawService;
        this.withdrawRepository = withdrawRepository;
    }


    @Scheduled(fixedDelayString = "${withdraw.scheduler.interval}", initialDelay = 10000)
    public void deleteWithdrawRequest(){

        int rawsDeleted = withdrawRepository.deletePendingWithdraws(new Timestamp(Timestamp.from(Instant.now()).getTime() - ONE_HOUR_IN_MILLIS));
        log.info("Delete old pending withdraw requests. Rows deleted = "+rawsDeleted);
    }


}
