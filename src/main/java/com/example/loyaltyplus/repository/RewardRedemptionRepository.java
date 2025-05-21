package com.example.loyaltyplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loyaltyplus.model.entity.RewardRedemption;

@Repository
public interface RewardRedemptionRepository extends JpaRepository<RewardRedemption, Long> {

}
