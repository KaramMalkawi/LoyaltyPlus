package com.example.loyaltyplus.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.loyaltyplus.model.dto.UserDto;
import com.example.loyaltyplus.model.entity.Role;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.repository.RoleRepository;
import com.example.loyaltyplus.repository.UserRepository;

@Service
@Primary
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User updateUser(Long id, UserDto dto) throws NoSuchElementException, IllegalArgumentException {
		if (dto == null) {
			throw new IllegalArgumentException("User DTO cannot be null");
		}

		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new NoSuchElementException("User not found with ID: " + id);
		}

		User existingUser = optionalUser.get();

		if (dto.getFirstName() != null) {
			existingUser.setFirstName(dto.getFirstName());
		}
		if (dto.getLastName() != null) {
			existingUser.setLastName(dto.getLastName());
		}
		if (dto.getUsername() != null) {
			existingUser.setUsername(dto.getUsername());
		}
		if (dto.getEmail() != null) {
			existingUser.setEmail(dto.getEmail());
		}
		if (dto.getPhone() != null) {
			existingUser.setPhone(dto.getPhone());
		}

		// Validate and set role
		if (dto.getRoleName() != null) {
			String roleName = dto.getRoleName().toUpperCase();
			if (!roleName.equals("SHOPPER") && !roleName.equals("STORE_MANAGER")
					&& !roleName.equals("MARKETING_ANALYST")) {
				throw new IllegalArgumentException(
						"Invalid role. Allowed roles: SHOPPER, STORE_MANAGER, MARKETING_ANALYST");
			}
			Role role = roleRepository.findByName(roleName)
					.orElseThrow(() -> new IllegalArgumentException("Role not found in database: " + roleName));
			existingUser.setRole(role);
		}

		if (dto.getPassword() != null) {
			existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		}

		existingUser.setUpdatedAt(new Date());

		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
