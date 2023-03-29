package com.business.app.service;

import com.example.data.dto.RedirectDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.util.TransactionServiceRequestHandler;
import com.example.data.model.*;
import com.example.data.repository.RedirectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
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

    public Redirect addRedirect(RedirectDto redirectDto)  {
        Redirect redirect = new Redirect();
        User user = userService.getUser(redirectDto.getUserId());
        Marketplace marketplace = marketplaceService.getMarketplace(redirectDto.getMarketplaceId());


        if (user != null && marketplace != null) {
            RedirectId redirectId = new RedirectId(user, marketplace);
            redirect.setPk(redirectId);

            redirect.setTime(Timestamp.valueOf(LocalDateTime.now()));
            redirectRepository.save(redirect);
            return redirect;
        } else {
            throw new NotFoundRedirectException("Not found user or marketplace");
        }
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

    @Scheduled(fixedRate = 600000)
    public void removeUnusedRedirects(){
        int count = 0;
        for (Redirect redirect: redirectRepository.findAll()){
            if ((Timestamp.from(Instant.now()).getTime() - redirect.getTime().getTime()) / 1000 > 300){
                removeRedirect(redirect);
                count++;
            }
        }
        if (count == 0)
            log.info("Scheduled redirects cleaning task: Not found unused redirects");
        else
            log.info("Scheduled redirects cleaning task: Successfully cleaned {} unused redirects", count);
    }

}
