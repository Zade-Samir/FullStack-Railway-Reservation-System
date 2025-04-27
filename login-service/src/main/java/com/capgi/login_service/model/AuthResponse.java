package com.capgi.login_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

//This will be returned after successful login with a JWT token

@Data
@AllArgsConstructor
public class AuthResponse {
	
	private String token;

}
