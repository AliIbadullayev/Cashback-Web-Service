package com.example.transaction_service.service;

import bitronix.tm.BitronixTransactionManager;
import com.example.data.dto.PurchaseApproveDto;
import com.example.data.dto.PurchaseFromMarketplaceDto;
import com.example.transaction_service.exception.NotHandledPurchaseException;
import com.example.data.model.*;
import com.example.data.repository.PurchaseRepository;
import com.example.data.repository.UserRepository;
import com.example.transaction_service.exception.TransactionException;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.sql.Timestamp;
import java.time.Instant;


@Service
public class PurchaseService {
    private final RedirectService redirectService;

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    private final BitronixTransactionManager transactionManager;

    public PurchaseService(RedirectService redirectService, PurchaseRepository purchaseRepository, UserRepository userRepository, BitronixTransactionManager transactionManager) {
        this.redirectService = redirectService;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.transactionManager = transactionManager;
    }

    private boolean checkRules(Rules rules, PurchaseFromMarketplaceDto purchase) {
        return rules.getMinPrice() <= purchase.getTotalPrice();
    }

    private boolean checkTimeDeadline(long started) {
        return (Timestamp.from(Instant.now()).getTime() - started) / 1000 <= 300;
    }

    public void purchaseAdd(PurchaseFromMarketplaceDto purchaseFromMarketplaceDto) throws SystemException {
        Purchase purchase = new Purchase();
        Redirect redirect = redirectService.getRedirect(purchaseFromMarketplaceDto.getUsername(), purchaseFromMarketplaceDto.getMarketplaceId());
        purchase.setStringIdentifier(purchaseFromMarketplaceDto.getStringIdentifier());
        purchase.setUser(redirect.getPk().getUser());
        purchase.setMarketplace(redirect.getPk().getMarketplace());
        purchase.setTimestamp(Timestamp.from(Instant.now()));
        purchase.setCashbackPercent(purchaseFromMarketplaceDto.getCashbackPercent());
        purchase.setTotalPrice(purchaseFromMarketplaceDto.getTotalPrice());
        try {
            transactionManager.begin();

            if (checkTimeDeadline(redirect.getTime().getTime())) {
                User user = purchase.getUser();
                if (checkRules(redirect.getPk().getMarketplace().getRules(), purchaseFromMarketplaceDto)) {
                    purchase.setCashbackPaymentStatus(Status.PENDING);
                    purchase.setRulesRespected(true);
                    user.setPendingBalance(user.getPendingBalance() + purchase.getCashbackPercent() * purchase.getTotalPrice() / 100);
                } else {
                    purchase.setCashbackPaymentStatus(Status.REJECTED);
                    purchase.setRulesRespected(false);
                }
                userRepository.save(user);
                purchaseRepository.save(purchase);
                redirectService.removeRedirect(redirect);
                transactionManager.commit();
            } else {
                throw new NotHandledPurchaseException("Not handled purchase because of time limit");
            }
        } catch (Exception e) {
            transactionManager.rollback();
            purchase.setCashbackPaymentStatus(Status.REJECTED);
            purchase.setErrorMessage(e.getMessage());
            purchaseRepository.save(purchase);
            throw new TransactionException("Ошибка выполнения транзакции: " + e.getMessage());
        }
    }

    public void approvePurchase(PurchaseApproveDto purchaseApproveDto) throws SystemException {
        try {
            transactionManager.begin();
            Purchase purchase = purchaseRepository.findByStringIdentifier(purchaseApproveDto.getStringIdentifier()).orElse(null);
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
                        purchase.setErrorMessage(null);
                        userRepository.save(user);
                        purchaseRepository.save(purchase);
                        transactionManager.commit();
                    } else {
                        throw new NotHandledPurchaseException("Cannot handle with rejected or approved purchase!");
                    }
                } else {
                    throw new NotHandledPurchaseException("Market that make request is not same as purchase market!");
                }
            } else {
                throw new NotHandledPurchaseException("Cannot find purchase with this id");
            }
        } catch (Exception e) {
            transactionManager.rollback();
            throw new TransactionException("Ошибка выполнения транзакции: " + e.getMessage());
        }
    }
}
