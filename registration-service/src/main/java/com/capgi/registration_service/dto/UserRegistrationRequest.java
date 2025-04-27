package com.capgi.registration_service.dto;

import lombok.Data;

@Data
public class UserRegistrationRequest {
	
	private String fullName;
	private String email;
	private String password; //will be stored as hashed

}
