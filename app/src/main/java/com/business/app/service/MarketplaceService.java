package com.business.app.service;

import com.business.app.exception.IllegalPageParametersException;
import com.business.app.exception.ResourceNotFoundException;
import com.business.app.model.Marketplace;
import com.business.app.repository.MarketplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarketplaceService {
    @Autowired
    MarketplaceRepository marketplaceRepository;


    public Page<Marketplace> getMarkeplacePage(int pageNum, int pageSize) {

        if (pageNum < 1 || pageSize < 1)
            throw new IllegalPageParametersException("Номер страницы и её размер должны быть больше 1");

        Pageable pageRequest = createPageRequest(pageNum - 1, pageSize);

        Page<Marketplace> resultPage = marketplaceRepository.findAll(pageRequest);

        if (resultPage.getTotalPages() < pageNum)
            throw new ResourceNotFoundException("На указанной странице не найдено записей!");

        return resultPage;
    }


    public Marketplace getMarketplace(Long marketplaceId) {
        return marketplaceRepository.findById(marketplaceId).orElse(null);
    }

    private Pageable createPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum, pageSize);
    }


}
