package com.business.app.service;

import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.RedirectDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.util.TransactionServiceRequestHandler;
import com.example.data.model.*;
import com.example.data.repository.RedirectRepository;
import jakarta.annotation.PostConstruct;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class RedirectService {
    @Autowired
    RedirectRepository redirectRepository;

    @Autowired
    UserService userService;

    @Autowired
    MarketplaceService marketplaceService;

    @Autowired
    TransactionServiceRequestHandler transactionServiceRequestHandler;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }


    public Redirect addRedirect(RedirectDto redirectDto, String url) throws NotFoundRedirectException {
        String newUrl = transactionServiceRequestHandler.generateUrlForTransactionService(url);
        HttpEntity<RedirectDto> entity = new HttpEntity<>(redirectDto);
        return restTemplate.postForObject(newUrl, entity, Redirect.class);
    }


    public Redirect getRedirect(String username, Long marketplaceId) throws NotFoundRedirectException {
        User user = userService.getUser(username);
        Marketplace marketplace = marketplaceService.getMarketplace(marketplaceId);

        if (user != null && marketplace != null) {
            RedirectId redirectId = new RedirectId(user, marketplace);
            Redirect redirect = redirectRepository.findById(redirectId).orElse(null);
            if (redirect != null) {
                return redirect;
            } else {
                throw new NotFoundRedirectException("Not found redirect");
            }
        } else {
            throw new NotFoundRedirectException("Not found redirect");
        }
    }

    public void removeRedirect(Redirect redirect) throws NotFoundRedirectException {
        redirectRepository.delete(redirect);
    }

}
