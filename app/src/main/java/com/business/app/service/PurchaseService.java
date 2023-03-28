package com.business.app.service;

import com.business.app.dto.PurchaseDto;
import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.PurchaseFromMarketplaceDto;
import com.business.app.exception.IllegalPageParametersException;
import com.business.app.exception.ResourceNotFoundException;
import com.business.app.util.IdGenerator;
import com.business.app.util.TransactionServiceRequestHandler;
import com.example.data.model.*;
import com.example.data.repository.PurchaseRepository;
import com.example.data.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    private final RedirectService redirectService;

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    private final TransactionServiceRequestHandler transactionServiceRequestHandler;

    private RestTemplate restTemplate;


    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;


    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public PurchaseService(RedirectService redirectService, PurchaseRepository purchaseRepository, UserRepository userRepository, TransactionServiceRequestHandler transactionServiceRequestHandler, JmsTemplate jmsTemplate, ObjectMapper mapper) {
        this.redirectService = redirectService;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.transactionServiceRequestHandler = transactionServiceRequestHandler;
        this.jmsTemplate = jmsTemplate;
        this.mapper = mapper;
    }

    public String purchaseAdd(PurchaseFromMarketplaceDto purchaseFromMarketplaceDto) throws JsonProcessingException {
        String id = IdGenerator.generateId();
        purchaseFromMarketplaceDto.setStringIdentifier(id);
        jmsTemplate.convertAndSend("purchase_add", mapper.writeValueAsString(purchaseFromMarketplaceDto));
        return id;
//        System.out.println(jmsTemplate.receive("purchase_add"));
//        HttpHeaders httpHeaders = transactionServiceRequestHandler.generateHttpHeader(token);
//        String newUrl = transactionServiceRequestHandler.generateUrl(url);
//        HttpEntity<PurchaseFromMarketplaceDto> entity = new HttpEntity<>(purchaseFromMarketplaceDto, httpHeaders);
//        return restTemplate.postForObject(newUrl, entity, Purchase.class);
    }

    public void approvePurchase(String purchaseId, PurchaseApproveDto purchaseApproveDto) throws JsonProcessingException {
        purchaseApproveDto.setStringIdentifier(purchaseId);
        jmsTemplate.convertAndSend("purchase_approve", mapper.writeValueAsString(purchaseApproveDto));
//        HttpHeaders httpHeaders = transactionServiceRequestHandler.generateHttpHeader(token);
//        String newUrl = transactionServiceRequestHandler.generateUrl(url);
//        HttpEntity<PurchaseApproveDto> entity = new HttpEntity<>(purchaseApproveDto, httpHeaders);
//        return restTemplate.postForObject(newUrl, entity, Purchase.class);
    }


    public List<PurchaseDto> getPurchasePage(User user, int pageNum, int pageSize) {

        if (pageNum < 1 || pageSize < 1)
            throw new IllegalPageParametersException("Номер страницы и её размер должны быть больше 1");

        Pageable pageRequest = createPageRequest(pageNum - 1, pageSize);

        Page<Purchase> resultPage = purchaseRepository.findAllByUser(user, pageRequest);

        if (resultPage.getTotalPages() < pageNum)
            throw new ResourceNotFoundException("На указанной странице не найдено записей!");

        List<PurchaseDto> resultList = new ArrayList<>();

        for (Purchase purchase : resultPage.getContent()) {
            resultList.add(PurchaseDto.fromPurchase(purchase));
        }

        return resultList;

    }


    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "timestamp");
    }


}
