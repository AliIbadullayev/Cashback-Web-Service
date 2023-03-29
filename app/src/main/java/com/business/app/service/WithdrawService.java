package com.business.app.service;

import com.business.app.util.IdGenerator;
import com.example.data.dto.WithdrawApproveDto;
import com.example.data.dto.WithdrawDto;
import com.example.data.dto.WithdrawApproveDto;
import com.example.data.dto.WithdrawDto;
import com.business.app.exception.NotHandledWithdrawException;
import com.business.app.util.TransactionServiceRequestHandler;
import com.example.data.model.*;
import com.example.data.repository.WithdrawRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {

    @Value("${withdraw.send.queue}")
    private String sendWithdrawQueue;
    @Value("${withdraw.approve.queue}")

    private String approveWithdrawQueue;
    private final WithdrawRepository withdrawRepository;

    private final TransactionServiceRequestHandler transactionServiceRequestHandler;

    private final JmsTemplate jmsTemplate;



    public WithdrawService(WithdrawRepository withdrawRepository, TransactionServiceRequestHandler transactionServiceRequestHandler, JmsTemplate jmsTemplate) {
        this.withdrawRepository = withdrawRepository;
        this.transactionServiceRequestHandler = transactionServiceRequestHandler;
        this.jmsTemplate = jmsTemplate;
    }

    public String sendWithdraw(WithdrawDto withdrawDto)  {
        String id = IdGenerator.generateId();
        withdrawDto.setStringIdentifier(id);
        jmsTemplate.convertAndSend(sendWithdrawQueue,withdrawDto);
        return id;
    }

    public Withdraw getWithdraw(String id) {
        Withdraw withdraw = withdrawRepository.findByStringIdentifier(id).orElse(null);
        System.out.println(withdraw);
        if (withdraw != null) {
            return withdraw;
        } else {
            throw new NotHandledWithdrawException("Cannot found withdraw with such id!");
        }
    }

    public void approveWithdraw(WithdrawApproveDto withdrawApproveDto, String stringIdentifier)  {
        withdrawApproveDto.setStringIdentifier(stringIdentifier);
        jmsTemplate.convertAndSend(approveWithdrawQueue,withdrawApproveDto);
    }
}
