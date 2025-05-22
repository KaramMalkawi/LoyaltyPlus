package com.example.loyaltyplus.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.loyaltyplus.model.dto.LoginUserDto;
import com.example.loyaltyplus.model.dto.UserDto;
import com.example.loyaltyplus.model.entity.Role;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.repository.RoleRepository;
import com.example.loyaltyplus.repository.UserRepository;

@Service
@Primary
public class AuthenticationService {

	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final RoleRepository roleRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public User signup(UserDto input) {
		String roleName = (input.getRoleName() != null) ? input.getRoleName().toUpperCase() : "SHOPPER";

		// Find the Role entity by name
		Role role = roleRepository.findByName(roleName)
				.orElseThrow(() -> new IllegalArgumentException("Invalid role: " + roleName));

		User user = new User();
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setUsername(input.getUsername());
		user.setPhone(input.getPhone());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setRole(role); // Now correctly sets Role object

		user.setCurrentPoints(0); // Initialize points to 0
		user.setCreatedAt(new Date());

		return userRepository.save(user);
	}

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

		return userRepository.findByUsername(input.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
