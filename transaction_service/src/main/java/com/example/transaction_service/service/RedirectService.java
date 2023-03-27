package com.example.transaction_service.service;

import com.example.transaction_service.exception.NotFoundRedirectException;
import com.example.data.model.Marketplace;
import com.example.data.model.Redirect;
import com.example.data.model.RedirectId;
import com.example.data.model.User;
import com.example.data.repository.RedirectRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
public class RedirectService {
    private final  RedirectRepository redirectRepository;

    private final  UserService userService;

    private final  MarketplaceService marketplaceService;

    public RedirectService(RedirectRepository redirectRepository, UserService userService, MarketplaceService marketplaceService) {
        this.redirectRepository = redirectRepository;
        this.userService = userService;
        this.marketplaceService = marketplaceService;
    }

    public Redirect getRedirect(String username, Long marketplaceId) throws NotFoundRedirectException {
        User user = userService.getUser(username);
        Marketplace marketplace = marketplaceService.getMarketplace(marketplaceId);

        if (user != null && marketplace != null) {
            Hibernate.initialize(marketplace.getRules());
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
