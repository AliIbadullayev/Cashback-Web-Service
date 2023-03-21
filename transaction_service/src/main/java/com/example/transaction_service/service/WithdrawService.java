package com.example.transaction_service.service;

import bitronix.tm.BitronixTransactionManager;
import com.example.transaction_service.dto.WithdrawApproveDto;
import com.example.transaction_service.dto.WithdrawDto;
import com.example.transaction_service.exception.NotHandledWithdrawException;
import com.example.data.model.*;
import com.example.data.repository.WithdrawRepository;
import com.example.transaction_service.exception.TransactionException;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class WithdrawService {
    private final WithdrawRepository withdrawRepository;

    private final UserService userService;

    private final PaymentMethodService paymentMethodService;

    private final BitronixTransactionManager transactionManager;

    public WithdrawService(WithdrawRepository withdrawRepository, UserService userService, PaymentMethodService paymentMethodService, BitronixTransactionManager transactionManager) {
        this.withdrawRepository = withdrawRepository;
        this.userService = userService;
        this.paymentMethodService = paymentMethodService;
        this.transactionManager = transactionManager;
    }

    public Withdraw sendWithdraw(WithdrawDto withdrawDto) throws SystemException, NotSupportedException {
        try {
            transactionManager.begin();

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
                        transactionManager.commit();
                    } else {
                        throw new NotHandledWithdrawException("Insufficient amount to withdraw! Min amount to withdraw is: " + paymentMethod.getMinAmount());
                    }
                } else {
                    throw new NotHandledWithdrawException("Payment credentials are wrong");
                }
            } else {
                throw new NotHandledWithdrawException("The amount to withdraw is more than available to you!");
            }
            return withdraw;
        } catch (Exception e) {
            transactionManager.rollback();
            throw new TransactionException("Ошибка выполнения транзакции: " + e.getMessage());
        }
    }

    public Withdraw getWithdraw(Long id) {
        Withdraw withdraw = withdrawRepository.findById(id).orElse(null);
        if (withdraw != null) {
            return withdraw;
        } else {
            throw new NotHandledWithdrawException("Cannot found withdraw with such id!");
        }
    }

    public Withdraw approveWithdraw(Long withdrawId, WithdrawApproveDto withdrawApproveDto) throws SystemException, NotSupportedException {
        transactionManager.begin();
        try {
            Withdraw withdraw = getWithdraw(withdrawId);
            User user = withdraw.getUser();
            double withdrawAmount = withdraw.getAmount() * (100 + withdraw.getPaymentMethod().getFee()) / 100;
            if (!withdraw.getWithdrawStatus().equals(Status.PENDING))
                throw new NotHandledWithdrawException("Cannot handle with rejected or approved withdraw!");
            if (withdrawApproveDto.getIsApproved()) {
                withdraw.setWithdrawStatus(Status.APPROVED);
            } else {
                user.setAvailableBalance(user.getAvailableBalance() + withdrawAmount);
                withdraw.setWithdrawStatus(Status.REJECTED);
            }
            userService.saveUser(user);
            withdraw.setUser(user);
            withdrawRepository.save(withdraw);
            transactionManager.commit();
            return withdraw;
        } catch (Exception e) {
            transactionManager.rollback();
            throw new TransactionException("Ошибка выполнения транзакции: " + e.getMessage());
        }
    }

    private boolean checkPaymentMethod(String credential, PaymentMethodTypes type) {
        switch (type) {
            case TYPE_CARD -> {
                return credential.length() == 16 && Long.parseLong(credential) < Math.pow(10, 17);
            }
            case TYPE_PHONE -> {
                int counter = 0;
                for (String c : credential.split("")) {
                    if (c.matches("[0-9]"))
                        counter++;
                }
                return counter == 11;
            }
            case TYPE_PERSONAL_ACCOUNT -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
