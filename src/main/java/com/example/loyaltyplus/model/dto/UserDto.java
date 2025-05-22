package com.example.loyaltyplus.model.dto;

import java.util.Date;

public class UserDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String phone;
	private String email;
	private String password;
	private Integer currentPoints;
	private Date createdAt;
	private Date updatedAt;
	private Long roleId;
	private String roleName;

	// Default constructor
	public UserDto() {
	}

	public UserDto(Long id, String firstName, String lastName, String username, String phone, String email,
			String password, Integer currentPoints, Date createdAt, Date updatedAt, Long roleId, String roleName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.currentPoints = currentPoints;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getCurrentPoints() {
		return currentPoints;
	}

	public void setCurrentPoints(Integer currentPoints) {
		this.currentPoints = currentPoints;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
