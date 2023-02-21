package com.business.app.service;

import com.business.app.model.Marketplace;
import com.business.app.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketplaceService {
    @Autowired
    MarketplaceRepository marketplaceRepository;

    public List<Marketplace> getMarketplaces() {
        return marketplaceRepository.findAll();
    }

    public Marketplace getMarketplace(Long marketplaceId) {
        return marketplaceRepository.findById(marketplaceId).orElse(null);
    }


}
