package com.example.loyaltyplus.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.loyaltyplus.model.dto.UserDto;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

//	public User updateUser(Long id, UserDto dto) {
//		Optional<User> optionalUser = userRepository.findById(id);
//		if (optionalUser.isPresent()) {
//			User existingUser = optionalUser.get();
//
//			if (dto.getFirstName() != null)
//				existingUser.setFirstName(dto.getFirstName());
//			if (dto.getLastName() != null)
//				existingUser.setLastName(dto.getLastName());
//			if (dto.getUsername() != null)
//				existingUser.setUsername(dto.getUsername());
//			if (dto.getEmail() != null)
//				existingUser.setEmail(dto.getEmail());
//			if (dto.getPhone() != null)
//				existingUser.setPhone(dto.getPhone());
//			if (dto.getRole() != null)
//				existingUser.setRole(dto.getRole());
//			if (dto.getPassword() != null) {
//				existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
//			}
//
//			return userRepository.save(existingUser);
//		}
//		return null;
//	}

	public User updateUser(Long id, UserDto dto) throws NoSuchElementException, IllegalArgumentException {
		// Validate input
		if (dto == null) {
			throw new IllegalArgumentException("User DTO cannot be null");
		}

		// Find existing user
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new NoSuchElementException("User not found with ID: " + id);
		}

		User existingUser = optionalUser.get();

		// Update fields if they are present in DTO
		if (dto.getFirstName() != null)
			existingUser.setFirstName(dto.getFirstName());
		if (dto.getLastName() != null)
			existingUser.setLastName(dto.getLastName());
		if (dto.getUsername() != null)
			existingUser.setUsername(dto.getUsername());
		if (dto.getEmail() != null)
			existingUser.setEmail(dto.getEmail());
		if (dto.getPhone() != null)
			existingUser.setPhone(dto.getPhone());
		if (dto.getRole() != null)
			existingUser.setRole(dto.getRole());
		if (dto.getPassword() != null)
			existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));

		// Update timestamps (optional)
		existingUser.setUpdatedAt(new Date());

		// Save and return the updated user
		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

}
