package com.business.app.repository;

import com.business.app.model.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<Marketplace,Long> {
}
