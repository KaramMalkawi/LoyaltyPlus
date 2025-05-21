package com.example.loyaltyplus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loyaltyplus.model.dto.RewardRedemptionDto;
import com.example.loyaltyplus.model.entity.RewardRedemption;
import com.example.loyaltyplus.service.RewardRedemptionService;

@RestController
@RequestMapping("/api/reward-redemptions")
public class RewardRedemptionController {

	@Autowired
	private final RewardRedemptionService rewardRedemptionService;

	public RewardRedemptionController(RewardRedemptionService rewardRedemptionService) {
		this.rewardRedemptionService = rewardRedemptionService;
	}

	@RequestMapping("/all")
	public List<RewardRedemption> getAllRewardRedemptions() {
		return rewardRedemptionService.getAllRewardRedemptions();
	}

	@RequestMapping("/find/{id}")
	public RewardRedemption getRewardRedemptionById(@PathVariable Long id) {
		return rewardRedemptionService.getRewardRedemptionById(id).orElse(null);
	}

	@RequestMapping("/create")
	public RewardRedemption createRewardRedemption(@RequestBody RewardRedemption rewardRedemption) {
		return rewardRedemptionService.createRewardRedemption(rewardRedemption);
	}

	@RequestMapping("/update/{id}")
	public RewardRedemption updateRewardRedemption(@PathVariable Long id,
			@RequestBody RewardRedemptionDto updatedRewardRedemptionDto) {
		return rewardRedemptionService.updateRewardRedemption(id, updatedRewardRedemptionDto);
	}

	@RequestMapping("/delete/{id}")
	public void deleteRewardRedemption(@PathVariable Long id) {
		rewardRedemptionService.deleteRewardRedemption(id);
	}

}
