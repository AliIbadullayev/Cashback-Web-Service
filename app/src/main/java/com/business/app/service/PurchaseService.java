package com.business.app.service;

import com.business.app.dto.PurchaseDto;
import com.business.app.model.Marketplace;
import com.business.app.model.Purchase;
import com.business.app.model.User;
import com.business.app.repository.MarketplaceRepository;
import com.business.app.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final MarketplaceRepository marketplaceRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, MarketplaceRepository marketplaceRepository) {
        this.purchaseRepository = purchaseRepository;
        this.marketplaceRepository = marketplaceRepository;
    }


    public List<PurchaseDto> getAllPurchasesByUser(User user){
        List<PurchaseDto> result = new ArrayList<>();
        for (Purchase purchase : purchaseRepository.findAllByUser(user)) {
            result.add(PurchaseDto.fromPurchase(purchase));
        }
        return result;
    }

//    private String getMarketplaceName(Long id){
//        Marketplace marketplace = marketplaceRepository.findById(id).orElse(null);
//        if (marketplace != null) return marketplace.getName();
//        else return "undefined";
//    }
}
