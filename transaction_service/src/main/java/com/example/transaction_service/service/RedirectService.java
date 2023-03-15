package com.example.transaction_service.service;

import bitronix.tm.BitronixTransactionManager;
import com.example.transaction_service.dto.RedirectDto;
import com.example.transaction_service.exception.NotFoundRedirectException;
import com.example.data.model.Marketplace;
import com.example.data.model.Redirect;
import com.example.data.model.RedirectId;
import com.example.data.model.User;
import com.example.data.repository.RedirectRepository;
import com.example.transaction_service.exception.TransactionException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class RedirectService {
    private final  RedirectRepository redirectRepository;

    private final  UserService userService;

    private final  MarketplaceService marketplaceService;

    private final BitronixTransactionManager transactionManager;

    public RedirectService(RedirectRepository redirectRepository, UserService userService, MarketplaceService marketplaceService, BitronixTransactionManager transactionManager) {
        this.redirectRepository = redirectRepository;
        this.userService = userService;
        this.marketplaceService = marketplaceService;
        this.transactionManager = transactionManager;
    }

    public Redirect addRedirect(RedirectDto redirectDto) throws NotFoundRedirectException, SystemException, NotSupportedException {
        transactionManager.begin();
        Transaction transaction = transactionManager.getCurrentTransaction();

        System.out.println("Current trans "+ transaction);

        try{
            Redirect redirect = new Redirect();
            User user = userService.getUser(redirectDto.getUserId());
            Marketplace marketplace = marketplaceService.getMarketplace(redirectDto.getMarketplaceId());


            if (user != null && marketplace != null) {
                RedirectId redirectId = new RedirectId(user, marketplace);
                redirect.setPk(redirectId);

                redirect.setTime(Timestamp.valueOf(LocalDateTime.now()));
                redirectRepository.save(redirect);
                transactionManager.commit();
                return redirect;
            } else {
                throw new NotFoundRedirectException("Not found user or marketplace");
            }


        } catch (Exception e){
            transactionManager.rollback();
            throw new TransactionException("Ошибка выполнения транзакции: "+e.getMessage());
        }

    }

    @Transactional
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
