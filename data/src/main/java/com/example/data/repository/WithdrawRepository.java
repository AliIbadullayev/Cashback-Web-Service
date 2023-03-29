package com.example.data.repository;

import com.example.data.model.Withdraw;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

    @EntityGraph(attributePaths = {"user", "paymentMethod"})
    @Override
    Optional<Withdraw> findById(Long aLong);


    @EntityGraph(attributePaths = {"user", "paymentMethod"})
    Optional<Withdraw> findByStringIdentifier(String strId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Withdraw w WHERE w.withdrawStatus = 'PENDING' AND w.time < :cutoffTime")
    int deletePendingWithdraws(@Param("cutoffTime") Timestamp cutoffTime);


}
