package com.capgi.login_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//this will represent login request payload

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
	
	private String email;
	private String password;

}
