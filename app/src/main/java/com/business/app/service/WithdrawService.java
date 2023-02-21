package com.business.app.service;

import com.business.app.dto.WithdrawDto;
import com.business.app.exception.NotHandledWithdrawException;
import com.business.app.model.*;
import com.business.app.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class WithdrawService {
    @Autowired
    WithdrawRepository withdrawRepository;

    @Autowired
    UserService userService;

    @Autowired
    PaymentMethodService paymentMethodService;

    public Withdraw sendWithdraw (WithdrawDto withdrawDto) {
        User user = userService.getUser(withdrawDto.getUsername());
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(withdrawDto.getPaymentMethodId());

        Withdraw withdraw = new Withdraw();
        if (withdrawDto.getAmount() * (100 + paymentMethod.getFee()) / 100 <= user.getAvailableBalance()) {
            if (checkPaymentMethod(withdrawDto.getCredential(), paymentMethod.getType())) {
                if (withdrawDto.getAmount() * (100 + paymentMethod.getFee()) / 100 >= paymentMethod.getMinAmount()) {
                    withdraw.setWithdrawStatus(Status.PENDING);
                    withdraw.setAmount(withdrawDto.getAmount());
                    withdraw.setTime(Timestamp.from(Instant.now()));
                    withdraw.setUser(user);
                    withdraw.setCredential(withdrawDto.getCredential());
                    withdraw.setPaymentMethod(paymentMethod);
                    user.setAvailableBalance(user.getAvailableBalance() - withdrawDto.getAmount() * (100 + paymentMethod.getFee()) / 100);
                    withdrawRepository.save(withdraw);
                    userService.saveUser(user);
                }else {
                    throw new NotHandledWithdrawException("Insufficient amount to withdraw! Min amount to withdraw is: " + paymentMethod.getMinAmount());
                }
            }else{
                throw new NotHandledWithdrawException("Payment credentials are wrong");
            }
        }else {
            throw new NotHandledWithdrawException("The amount to withdraw is more than available to you!");
        }
        return withdraw;
    }

    private boolean checkPaymentMethod(String credential, PaymentMethodTypes type){
        switch (type){
            case TYPE_CARD -> {
                return credential.length() == 16 && Long.parseLong(credential) < Math.pow(10, 17);
            }
            case TYPE_PHONE -> {
                int counter = 0;
                for(String c: credential.split("")){
                    if (c.matches("[0-9]"))
                        counter++;
                }
                return counter == 11;
            }
            case TYPE_PERSONAL_ACCOUNT ->
            {
                return true;
            }
            default ->{
                return false;
            }
        }
    }
}
