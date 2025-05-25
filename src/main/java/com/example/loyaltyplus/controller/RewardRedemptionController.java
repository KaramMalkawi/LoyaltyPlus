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

import com.example.loyaltyplus.model.dto.RewardRedemptionDto;
import com.example.loyaltyplus.model.entity.RewardRedemption;
import com.example.loyaltyplus.service.RewardRedemptionService;

@RestController
@RequestMapping("/reward-redemptions")
public class RewardRedemptionController {

	@Autowired
	private final RewardRedemptionService rewardRedemptionService;

	public RewardRedemptionController(RewardRedemptionService rewardRedemptionService) {
		this.rewardRedemptionService = rewardRedemptionService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<RewardRedemption>> getAllRewardRedemptions() {
		List<RewardRedemption> redemptions = rewardRedemptionService.getAllRewardRedemptions();
		return ResponseEntity.ok(redemptions);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<RewardRedemption> getRewardRedemptionById(@PathVariable Long id) {
		return rewardRedemptionService.getRewardRedemptionById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<RewardRedemptionDto>> getRewardRedemptionsByUserId(@PathVariable Long userId) {
		List<RewardRedemption> redemptions = rewardRedemptionService.getRewardRedemptionsByUserId(userId);
		List<RewardRedemptionDto> dtos = redemptions.stream().map(RewardRedemptionDto::fromEntity).toList();
		return ResponseEntity.ok(dtos);
	}

	@PostMapping("/create")
	public ResponseEntity<RewardRedemptionDto> createRewardRedemption(@RequestBody RewardRedemptionDto dto) {
		RewardRedemption created = rewardRedemptionService.createRewardRedemptionFromDto(dto);
		return ResponseEntity.ok(RewardRedemptionDto.fromEntity(created));
	}

//	@PostMapping("/create")
//	public ResponseEntity<RewardRedemption> createRewardRedemption(@RequestBody RewardRedemption rewardRedemption) {
//		RewardRedemption created = rewardRedemptionService.createRewardRedemption(rewardRedemption);
//		return ResponseEntity.ok(created);
//	}

	@PostMapping("/update/{id}")
	public ResponseEntity<RewardRedemption> updateRewardRedemption(@PathVariable Long id,
			@RequestBody RewardRedemptionDto updatedRewardRedemptionDto) {
		RewardRedemption updated = rewardRedemptionService.updateRewardRedemption(id, updatedRewardRedemptionDto);
		return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteRewardRedemption(@PathVariable Long id) {
		rewardRedemptionService.deleteRewardRedemption(id);
		return ResponseEntity.noContent().build();
	}
}
