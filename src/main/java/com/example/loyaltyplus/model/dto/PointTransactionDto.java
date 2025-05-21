package com.example.loyaltyplus.model.dto;

import java.util.Date;

public class PointTransactionDto {

	private long id;
	private int pointsChanged;
	private String description;
	private Date createdAt;
	private Date updatedAt;
	private Long userId;

	/* Constructor */
	public PointTransactionDto(long id, int pointsChanged, String description, Date createdAt, Date updatedAt,
			Long userId) {
		this.id = id;
		this.pointsChanged = pointsChanged;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
	}

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPointsChanged() {
		return pointsChanged;
	}

	public void setPointsChanged(int pointsChanged) {
		this.pointsChanged = pointsChanged;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
