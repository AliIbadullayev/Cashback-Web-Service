package com.business.app.service;

import com.business.app.dto.RedirectDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.model.Marketplace;
import com.business.app.model.Redirect;
import com.business.app.model.RedirectId;
import com.business.app.model.User;
import com.business.app.repository.RedirectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class RedirectService {
    @Autowired
    RedirectRepository redirectRepository;

    @Autowired
    UserService userService;

    @Autowired
    MarketplaceService marketplaceService;

    public Redirect addRedirect(RedirectDto redirectDto) throws NotFoundRedirectException {
        Redirect redirect = new Redirect();
        User user = userService.getUser(redirectDto.getUserId());
        Marketplace marketplace = marketplaceService.getMarketplace(redirectDto.getMarketplaceId());


        if (user != null && marketplace != null){
            RedirectId redirectId = new RedirectId(user, marketplace);
            redirect.setPk(redirectId);
            redirect.setTime(Timestamp.valueOf(LocalDateTime.now()));
            redirectRepository.save(redirect);
            return redirect;
        }
        else {
            throw new NotFoundRedirectException("Not found user or marketplace");
        }
    }

    public Redirect getRedirect(String username, Long marketplaceId) throws NotFoundRedirectException {
        User user = userService.getUser(username);
        Marketplace marketplace = marketplaceService.getMarketplace(marketplaceId);

        if (user != null && marketplace != null){
            RedirectId redirectId = new RedirectId(user, marketplace);
            Redirect redirect = redirectRepository.findById(redirectId).orElse(null);
            if (redirect != null){
                return redirect;
            } else {
                throw new NotFoundRedirectException("Not found redirect");
            }
        }
        else {
            throw new NotFoundRedirectException("Not found redirect");
        }
    }

    public void removeRedirect(Redirect redirect) throws NotFoundRedirectException {
        redirectRepository.delete(redirect);
    }

}
