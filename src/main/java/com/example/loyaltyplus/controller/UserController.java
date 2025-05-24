package com.example.loyaltyplus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loyaltyplus.model.dto.UserDto;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/shoppers")
	public ResponseEntity<List<User>> getAllShoppers() {
		return ResponseEntity.ok(userService.getAllShoppers());
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
		try {
			return ResponseEntity.ok(userService.updateUser(id, userDto));
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/update/points/{id}")
	public ResponseEntity<Map<String, String>> updatePoints(@PathVariable Long id,
			@RequestBody Map<String, Integer> body) {
		int newPoints = body.get("currentPoints");
		userService.updateUserPoints(id, newPoints);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Points updated successfully");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/details")
	public ResponseEntity<User> getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		return userService.getUserByUsername(currentPrincipalName).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

}
