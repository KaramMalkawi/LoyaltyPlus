package com.example.loyaltyplus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loyaltyplus.model.entity.PointTransaction;

@Repository
public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {

}
