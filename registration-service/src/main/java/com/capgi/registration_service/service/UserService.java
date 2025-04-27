package com.capgi.registration_service.service;

import java.util.List;

import com.capgi.registration_service.dto.UserRegistrationRequest;
import com.capgi.registration_service.model.User;

public interface UserService {
	
	 User registerUser(UserRegistrationRequest request);
	 User getUserById(Long id);
	 List<User> getAllUsers();

}
