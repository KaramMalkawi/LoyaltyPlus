package com.example.loyaltyplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loyaltyplus.model.entity.Reward;
import com.example.loyaltyplus.service.RewardService;

@RestController
@RequestMapping("/rewards")
public class RewardController {

	@Autowired
	private final RewardService rewardService;

	public RewardController(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Reward>> getAllRewards() {
		List<Reward> rewards = rewardService.getAllRewards();
		return ResponseEntity.ok(rewards);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Reward> getRewardById(@PathVariable Long id) {
		return rewardService.getRewardById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
		Reward newReward = rewardService.createReward(reward);
		return ResponseEntity.ok(newReward);
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<Reward> updateReward(@PathVariable Long id, @RequestBody Reward updatedReward) {
		Reward updated = rewardService.updateReward(id, updatedReward);
		return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteReward(@PathVariable Long id) {
		rewardService.deleteReward(id);
		return ResponseEntity.noContent().build();
	}
}
