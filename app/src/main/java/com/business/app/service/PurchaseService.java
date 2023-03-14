package com.business.app.service;

import com.business.app.dto.PurchaseDto;
import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.PurchaseFromMarketplaceDto;
import com.business.app.exception.IllegalPageParametersException;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotHandledPurchaseException;
import com.business.app.exception.ResourceNotFoundException;
import com.business.app.util.TransactionServiceRequestHandler;
import com.example.data.model.*;
import com.example.data.repository.PurchaseRepository;
import com.example.data.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
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

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public PurchaseService(RedirectService redirectService, PurchaseRepository purchaseRepository, UserRepository userRepository, TransactionServiceRequestHandler transactionServiceRequestHandler) {
        this.redirectService = redirectService;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.transactionServiceRequestHandler = transactionServiceRequestHandler;
    }

    public Purchase purchaseAdd(PurchaseFromMarketplaceDto purchaseFromMarketplaceDto, String url) throws NotFoundRedirectException, NotHandledPurchaseException {
        String newUrl = transactionServiceRequestHandler.generateUrl(url);
        HttpEntity<PurchaseFromMarketplaceDto> entity = new HttpEntity<>(purchaseFromMarketplaceDto);
        return restTemplate.postForObject(newUrl, entity, Purchase.class);
    }

    public Purchase approvePurchase(PurchaseApproveDto purchaseApproveDto, String url) {
        String newUrl = transactionServiceRequestHandler.generateUrl(url);
        HttpEntity<PurchaseApproveDto> entity = new HttpEntity<>(purchaseApproveDto);
        return restTemplate.postForObject(newUrl, entity, Purchase.class);
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
