package com.business.app.service;

import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.PurchaseFromMarketplaceDto;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotHandledPurchaseException;
import com.business.app.model.*;
import com.business.app.repository.PurchaseRepository;
import com.business.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class PurchaseService {
    @Autowired
    RedirectService redirectService;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    UserRepository userRepository;

    private boolean checkRules(Rules rules, PurchaseFromMarketplaceDto purchase){
        return rules.getMinPrice() <= purchase.getTotalPrice();
    }

    private boolean checkTimeDeadline(long started ){
        return (Timestamp.from(Instant.now()).getTime() - started)/1000 <= 30;
    }

    public Purchase purchaseAdd(PurchaseFromMarketplaceDto purchaseFromMarketplaceDto) throws NotFoundRedirectException, NotHandledPurchaseException {
        Purchase purchase = new Purchase();
        Redirect redirect = redirectService.getRedirect(purchaseFromMarketplaceDto.getUsername(), purchaseFromMarketplaceDto.getMarketplaceId());
        if (checkTimeDeadline(redirect.getTime().getTime())){
            purchase.setUser(redirect.getPk().getUser());
            purchase.setMarketplace(redirect.getPk().getMarketplace());
            purchase.setTimestamp(Timestamp.from(Instant.now()));
            purchase.setCashbackPercent(purchaseFromMarketplaceDto.getCashbackPercent());
            purchase.setTotalPrice(purchaseFromMarketplaceDto.getTotalPrice());
            User user = purchase.getUser();
            if (checkRules(redirect.getPk().getMarketplace().getRules(), purchaseFromMarketplaceDto)){
                purchase.setCashbackPaymentStatus(Status.PENDING);
                purchase.setRulesRespected(true);
                user.setPendingBalance(user.getPendingBalance() + purchase.getCashbackPercent()*purchase.getTotalPrice()/100);
            } else {
                purchase.setCashbackPaymentStatus(Status.REJECTED);
                purchase.setRulesRespected(false);
            }
            userRepository.save(user);
            purchaseRepository.save(purchase);
            redirectService.removeRedirect(redirect);
            return purchase;
        } else {
            throw new NotHandledPurchaseException("Not handled purchase because of time limit");
        }
    }

    public Purchase approvePurchase(PurchaseApproveDto purchaseApproveDto) {
        Purchase purchase = purchaseRepository.findById(purchaseApproveDto.getPurchaseId()).orElse(null);
        if (purchase != null) {
            if (purchase.getMarketplace().getId().equals(purchaseApproveDto.getMarketplaceId())) {
                if (purchase.isRulesRespected() && purchase.getCashbackPaymentStatus() == Status.PENDING) {
                    User user = purchase.getUser();
                    if (purchaseApproveDto.getIsApproved()) {
                        user.setAvailableBalance(user.getAvailableBalance() + purchase.getTotalPrice() * purchase.getCashbackPercent() / 100);
                        purchase.setCashbackPaymentStatus(Status.APPROVED);
                    } else {
                        purchase.setCashbackPaymentStatus(Status.REJECTED);
                    }
                    user.setPendingBalance(user.getPendingBalance() - purchase.getTotalPrice() * purchase.getCashbackPercent() / 100);
                    userRepository.save(user);
                    purchaseRepository.save(purchase);
                } else {
                    throw new NotHandledPurchaseException("Cannot handle with rejected or approved purchase!");
                }
            } else {
                throw new NotHandledPurchaseException("Market that make request is not same as purchase market!");
            }
        } else {
            throw new NotHandledPurchaseException("Cannot find purchase with this id");
        }
        return purchase;
    }
}
