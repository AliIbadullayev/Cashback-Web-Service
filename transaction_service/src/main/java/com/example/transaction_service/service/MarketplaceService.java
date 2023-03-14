package com.example.transaction_service.service;

import com.example.data.model.Marketplace;
import com.example.data.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketplaceService {
    @Autowired
    MarketplaceRepository marketplaceRepository;

    public Marketplace getMarketplace(Long marketplaceId) {
        return marketplaceRepository.findById(marketplaceId).orElse(null);
    }

}
