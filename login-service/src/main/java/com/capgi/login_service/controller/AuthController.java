package com.capgi.login_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgi.login_service.model.AuthRequest;
import com.capgi.login_service.model.AuthResponse;
import com.capgi.login_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	//accept the JSON and wrap it into token
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
		
		String token = authService.authenticateAndGenerateToken(request);
		
		return ResponseEntity.ok(new AuthResponse(token));
	}

}
