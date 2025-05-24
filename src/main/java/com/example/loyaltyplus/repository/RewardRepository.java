package com.example.loyaltyplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.loyaltyplus.model.entity.Reward;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

	@Query("SELECT r FROM Reward r WHERE r.isActive = true")
	List<Reward> findAllActiveRewards();

}
