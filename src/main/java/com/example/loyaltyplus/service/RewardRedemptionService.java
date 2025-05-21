package com.example.loyaltyplus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loyaltyplus.model.dto.RewardRedemptionDto;
import com.example.loyaltyplus.model.entity.Reward;
import com.example.loyaltyplus.model.entity.RewardRedemption;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.repository.RewardRedemptionRepository;
import com.example.loyaltyplus.repository.RewardRepository;
import com.example.loyaltyplus.repository.UserRepository;

@Service
public class RewardRedemptionService {

	private final RewardRedemptionRepository rewardRedemptionRepository;
	private final UserRepository userRepository;
	private final RewardRepository rewardRepository;

	@Autowired
	public RewardRedemptionService(RewardRedemptionRepository rewardRedemptionRepository, UserRepository userRepository,
			RewardRepository rewardRepository) {
		this.rewardRedemptionRepository = rewardRedemptionRepository;
		this.userRepository = userRepository;
		this.rewardRepository = rewardRepository;
	}

	public List<RewardRedemption> getAllRewardRedemptions() {
		return rewardRedemptionRepository.findAll();
	}

	public Optional<RewardRedemption> getRewardRedemptionById(Long id) {
		return rewardRedemptionRepository.findById(id);
	}

	public RewardRedemption createRewardRedemption(RewardRedemption rewardRedemption) {
		return rewardRedemptionRepository.save(rewardRedemption);
	}

	public RewardRedemption updateRewardRedemption(Long id, RewardRedemptionDto updatedRewardRedemptionDto) {
		RewardRedemption existingRewardRedemption = rewardRedemptionRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("RewardRedemption with id " + id + " was not found"));

		if (updatedRewardRedemptionDto.getUserId() != null) {
			User user = userRepository.findById(updatedRewardRedemptionDto.getUserId())
					.orElseThrow(() -> new IllegalArgumentException(
							"User with id " + updatedRewardRedemptionDto.getUserId() + " was not found"));
			existingRewardRedemption.setUser(user);
		}

		if (updatedRewardRedemptionDto.getRewardId() != null) {
			Reward reward = rewardRepository.findById(updatedRewardRedemptionDto.getRewardId())
					.orElseThrow(() -> new IllegalArgumentException(
							"Reward with id " + updatedRewardRedemptionDto.getRewardId() + " was not found"));
			existingRewardRedemption.setReward(reward);
		}

		return rewardRedemptionRepository.save(existingRewardRedemption);
	}

	public void deleteRewardRedemption(Long id) {
		if (!rewardRedemptionRepository.existsById(id)) {
			throw new IllegalArgumentException("RewardRedemption with id " + id + " was not found");
		}
		rewardRedemptionRepository.deleteById(id);
	}

}
