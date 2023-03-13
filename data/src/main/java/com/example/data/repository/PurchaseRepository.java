package com.example.data.repository;

import com.example.data.model.Purchase;
import com.example.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Page<Purchase> findAllByUser(User user, Pageable pageable);


    @EntityGraph(attributePaths = {"user", "marketplace.rules"})
    @Override
    Optional<Purchase> findById(Long aLong);
}