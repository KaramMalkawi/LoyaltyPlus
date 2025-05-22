package com.example.loyaltyplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loyaltyplus.authentication.JwtService;
import com.example.loyaltyplus.authentication.LoginResponse;
import com.example.loyaltyplus.model.dto.LoginUserDto;
import com.example.loyaltyplus.model.dto.UserDto;
import com.example.loyaltyplus.model.entity.User;
import com.example.loyaltyplus.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

	private final JwtService jwtService;
	private AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody UserDto registerUserDto) {
		User registeredUser = authenticationService.signup(registerUserDto);
		System.err.print(registeredUser);
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());

		return ResponseEntity.ok(loginResponse);

	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response,
				SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}

}
