package com.capgi.registration_service.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capgi.registration_service.dto.UserRegistrationRequest;
import com.capgi.registration_service.model.User;
import com.capgi.registration_service.repository.UserRepository;
import com.capgi.registration_service.service.UserService;

import lombok.RequiredArgsConstructor;

//Spring automatically injects the only class that implements that interface — in this case, UserServiceImpl.
//hence, we are using UserServiceImpl that interface the UserService


@Service
@RequiredArgsConstructor //it will automatically inject required constructor injections	
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; //service interface for encoding password
	
	
	//we are register new user
	@Override
	public User registerUser(UserRegistrationRequest request) {
		
		//if already exists
		if(userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists!");
		}
		
		//if already not exists
		User user = new User();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword())); //convert into hashed string
		user.setRole("USER");
		
		return userRepository.save(user); //saving user data
	}
	
	
	@Override
	public User getUserById(Long id) {
		
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not Found"));
	}
	
	
	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}
	
	

}
