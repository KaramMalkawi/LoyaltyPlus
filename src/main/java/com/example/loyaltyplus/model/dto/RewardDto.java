package com.example.loyaltyplus.model.dto;

import java.util.Date;
import java.util.List;

public class RewardDto {

	private Long id;
	private String title;
	private String description;
	private int pointsRequired;
	private boolean isActive;
	private Date createdAt;
	private Date updatedAt;
	private List<RewardRedemptionDto> redemptions; // DTO for nested objects

	// Constructors
	public RewardDto() {
	}

	public RewardDto(Long id, String title, String description, int pointsRequired, boolean isActive, Date createdAt,
			Date updatedAt, List<RewardRedemptionDto> redemptions) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.pointsRequired = pointsRequired;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.redemptions = redemptions;
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

	public List<RewardRedemptionDto> getRedemptions() {
		return redemptions;
	}

	public void setRedemptions(List<RewardRedemptionDto> redemptions) {
		this.redemptions = redemptions;
	}
}