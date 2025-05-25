package com.example.loyaltyplus.model.dto;

import java.util.Date;

import com.example.loyaltyplus.model.entity.RewardRedemption;

public class RewardRedemptionDto {

	private Long id;
	private Long userId;
	private Long rewardId;
	private Date createdAt;
	private Date updatedAt;

	// Constructors
	public RewardRedemptionDto() {
	}

	public RewardRedemptionDto(Long id, Long userId, Long rewardId, Date createdAt, Date updatedAt) {
		this.id = id;
		this.userId = userId;
		this.rewardId = rewardId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRewardId() {
		return rewardId;
	}

	public void setRewardId(Long rewardId) {
		this.rewardId = rewardId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	// Optional: Helper method to create DTO from Entity
	public static RewardRedemptionDto fromEntity(RewardRedemption entity) {
		return new RewardRedemptionDto(entity.getId(), entity.getUser() != null ? entity.getUser().getId() : null,
				entity.getReward() != null ? entity.getReward().getId() : null, entity.getCreatedAt(),
				entity.getUpdatedAt());
	}

}