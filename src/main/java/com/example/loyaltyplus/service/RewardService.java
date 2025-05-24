package com.example.loyaltyplus.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loyaltyplus.model.entity.Reward;
import com.example.loyaltyplus.repository.RewardRepository;

@Service
public class RewardService {
	@Autowired
	private RewardRepository rewardRepository;

	public RewardService(RewardRepository rewardRepository) {
		this.rewardRepository = rewardRepository;
	}

	public List<Reward> getAllRewards() {
		return rewardRepository.findAll();
	}

	public Optional<Reward> getRewardById(Long id) {
		return rewardRepository.findById(id);
	}

	public List<Reward> getActiveRewards() {
		return rewardRepository.findAllActiveRewards();
	}

	public Reward createReward(Reward reward) {
		Reward newReward = new Reward();

		// Set the properties of the new reward
		newReward.setTitle(reward.getTitle());
		newReward.setDescription(reward.getDescription());
		newReward.setPointsRequired(reward.getPointsRequired());
		newReward.setActive(reward.isActive());
		newReward.setCreatedAt(new Date());

		newReward.setRedemptions(reward.getRedemptions());
		// Save the new reward to the database
		return rewardRepository.save(newReward);
	}

	public Reward updateReward(Long id, Reward updatedReward) {
		Optional<Reward> existingRewardOptional = rewardRepository.findById(id);

		if (existingRewardOptional.isPresent()) {
			Reward existingReward = existingRewardOptional.get();

			if (updatedReward.getTitle() != null)
				existingReward.setTitle(updatedReward.getTitle());
			if (updatedReward.getDescription() != null)
				existingReward.setDescription(updatedReward.getDescription());
			if (updatedReward.getPointsRequired() != 0)
				existingReward.setPointsRequired(updatedReward.getPointsRequired());
			if (updatedReward.isActive())
				existingReward.setActive(updatedReward.isActive());

			existingReward.setUpdatedAt(new Date());

			return rewardRepository.save(existingReward);
		} else {
			return null;
		}
	}

	public void deleteReward(Long id) {
		rewardRepository.deleteById(id);
	}
}
