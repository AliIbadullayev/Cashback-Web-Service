package com.business.app.service;

import com.business.app.dto.WithdrawApproveDto;
import com.business.app.dto.WithdrawDto;
import com.business.app.exception.NotHandledWithdrawException;
import com.business.app.util.TransactionServiceRequestHandler;
import com.example.data.model.*;
import com.example.data.repository.WithdrawRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WithdrawService {
    @Autowired
    WithdrawRepository withdrawRepository;

    @Autowired
    UserService userService;

    @Autowired
    PaymentMethodService paymentMethodService;

    @Autowired
    TransactionServiceRequestHandler transactionServiceRequestHandler;

    private RestTemplate restTemplate;


    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public Withdraw sendWithdraw(WithdrawDto withdrawDto, String url, String token)  {
        HttpHeaders httpHeaders = transactionServiceRequestHandler.generateHttpHeader(token);
        String newUrl = transactionServiceRequestHandler.generateUrl(url);
        HttpEntity<WithdrawDto> entity = new HttpEntity<>(withdrawDto, httpHeaders);
        return restTemplate.postForObject(newUrl, entity, Withdraw.class);
    }

    public Withdraw getWithdraw(Long id) {
        Withdraw withdraw = withdrawRepository.findById(id).orElse(null);
        if (withdraw != null) {
            return withdraw;
        } else {
            throw new NotHandledWithdrawException("Cannot found withdraw with such id!");
        }
    }

    public Withdraw approveWithdraw(WithdrawApproveDto withdrawApproveDto, String url, String token)  {
        HttpHeaders httpHeaders = transactionServiceRequestHandler.generateHttpHeader(token);
        String newUrl = transactionServiceRequestHandler.generateUrl(url);
        HttpEntity<WithdrawApproveDto> entity = new HttpEntity<>(withdrawApproveDto, httpHeaders);
        return restTemplate.postForObject(newUrl, entity, Withdraw.class);
    }
}
