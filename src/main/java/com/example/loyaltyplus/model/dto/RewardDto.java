package com.example.loyaltyplus.model.dto;

import java.util.Date;

import com.example.loyaltyplus.model.entity.Reward;

public class RewardDto {

	private Long id;
	private String title;
	private String description;
	private int pointsRequired;
	private boolean isActive;
	private Date createdAt;
	private Date updatedAt;

	public static RewardDto toDto(Reward reward) {
		return new RewardDto(reward.getId(), reward.getTitle(), reward.getDescription(), reward.getPointsRequired(),
				reward.isActive(), reward.getCreatedAt(), reward.getUpdatedAt());
	}

	// Constructors
	public RewardDto() {
	}

	public RewardDto(Long id, String title, String description, int pointsRequired, boolean isActive, Date createdAt,
			Date updatedAt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.pointsRequired = pointsRequired;
		this.isActive = isActive;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPointsRequired() {
		return pointsRequired;
	}

	public void setPointsRequired(int pointsRequired) {
		this.pointsRequired = pointsRequired;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
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

}