package com.example.loyaltyplus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loyaltyplus.model.entity.Reward;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

}
