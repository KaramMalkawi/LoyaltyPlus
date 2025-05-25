package com.example.loyaltyplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loyaltyplus.model.entity.RewardRedemption;

@Repository
public interface RewardRedemptionRepository extends JpaRepository<RewardRedemption, Long> {

	List<RewardRedemption> findByUserId(Long userId);

	List<RewardRedemption> findByRewardId(Long rewardId);
}
