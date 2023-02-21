package com.business.app.repository;

import com.business.app.model.Purchase;
import com.business.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByUser(User user);

}