package com.business.app.service;

import com.business.app.dto.PurchaseDto;
import com.business.app.dto.PurchaseApproveDto;
import com.business.app.dto.PurchaseFromMarketplaceDto;
import com.business.app.exception.IllegalPageParametersException;
import com.business.app.exception.NotFoundRedirectException;
import com.business.app.exception.NotHandledPurchaseException;
import com.business.app.exception.ResourceNotFoundException;
import com.example.data.model.*;
import com.example.data.repository.PurchaseRepository;
import com.example.data.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {
    private final RedirectService redirectService;

    private final PurchaseRepository purchaseRepository;

    private final UserRepository userRepository;

    public PurchaseService(RedirectService redirectService, PurchaseRepository purchaseRepository, UserRepository userRepository) {
        this.redirectService = redirectService;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    private boolean checkRules(Rules rules, PurchaseFromMarketplaceDto purchase) {
        return rules.getMinPrice() <= purchase.getTotalPrice();
    }

    private boolean checkTimeDeadline(long started) {
        return (Timestamp.from(Instant.now()).getTime() - started) / 1000 <= 3000;
    }

    public Purchase purchaseAdd(PurchaseFromMarketplaceDto purchaseFromMarketplaceDto) throws NotFoundRedirectException, NotHandledPurchaseException {
        Purchase purchase = new Purchase();
        Redirect redirect = redirectService.getRedirect(purchaseFromMarketplaceDto.getUsername(), purchaseFromMarketplaceDto.getMarketplaceId());
        if (checkTimeDeadline(redirect.getTime().getTime())) {
            purchase.setUser(redirect.getPk().getUser());
            purchase.setMarketplace(redirect.getPk().getMarketplace());
            purchase.setTimestamp(Timestamp.from(Instant.now()));
            purchase.setCashbackPercent(purchaseFromMarketplaceDto.getCashbackPercent());
            purchase.setTotalPrice(purchaseFromMarketplaceDto.getTotalPrice());
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
            return purchase;
        } else {
            throw new NotHandledPurchaseException("Not handled purchase because of time limit");
        }
    }

    public Purchase approvePurchase(Long purchaseId, PurchaseApproveDto purchaseApproveDto) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElse(null);
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
