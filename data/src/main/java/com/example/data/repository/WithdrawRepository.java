package com.example.data.repository;

import com.example.data.model.Withdraw;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

    @EntityGraph(attributePaths = {"user", "paymentMethod"})
    @Override
    Optional<Withdraw> findById(Long aLong);
}
