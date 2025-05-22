package com.example.loyaltyplus.model.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "rewards", indexes = { @Index(name = "idx_reward_title", columnList = "title", unique = true) })
public class Reward {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "title", nullable = false, unique = true)
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "points_required", nullable = false)
	private int pointsRequired;

	@Column(name = "is_active", nullable = false)
	private boolean isActive;

	@OneToMany(mappedBy = "reward", cascade = CascadeType.ALL)
	private List<RewardRedemption> redemptions;

	@Column(name = "created_at", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	// Constructors
	public Reward() {
	}

	public Reward(String title, String description, int pointsRequired, boolean isActive, Date createdAt,
			Date updatedAt) {
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

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<RewardRedemption> getRedemptions() {
		return redemptions;
	}

	public void setRedemptions(List<RewardRedemption> redemptions) {
		this.redemptions = redemptions;
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
