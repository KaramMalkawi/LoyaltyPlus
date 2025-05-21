package com.example.loyaltyplus.model.dto;

public class LoginUserDto {

	private String username;
	private String password;

	// Default constructor
	public LoginUserDto() {
	}

	// Parameterized constructor
	public LoginUserDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
