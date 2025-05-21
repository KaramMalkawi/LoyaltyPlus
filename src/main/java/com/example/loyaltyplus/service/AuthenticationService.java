package com.example.loyaltyplus.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.loyaltyplus.model.dto.LoginUserDto;
import com.example.loyaltyplus.model.dto.UserDto;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.repository.UserRepository;

public class AuthenticationService {

	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public User signup(UserDto input) {
		User user = new User();
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setUsername(input.getUsername());
		user.setPhone(input.getPhone());
		user.setEmail(input.getEmail());
		user.setRole(input.getRole() != null ? input.getRole() : "USER");
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		return userRepository.save(user);
	}

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

		return userRepository.findByUsername(input.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
