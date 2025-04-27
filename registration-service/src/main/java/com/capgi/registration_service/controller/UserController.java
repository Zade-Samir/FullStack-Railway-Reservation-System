package com.capgi.registration_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgi.registration_service.dto.UserRegistrationRequest;
import com.capgi.registration_service.model.User;
import com.capgi.registration_service.repository.UserRepository;
import com.capgi.registration_service.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	
	//final - helpful for testability and immutability
	private final UserService userService;
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody UserRegistrationRequest request) {
		
		return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id)); //create responseEntity with body and status = ok
	}
	
	//get all the users
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@PostMapping("/validate")
	public ResponseEntity<String> validateUser(@RequestBody UserRegistrationRequest request) {
	    try {
	        // Find user by email
	        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
	        
	        // If user exists and password matches
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	                return ResponseEntity.ok(user.getEmail());
	            }
	        }
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Validation error: " + e.getMessage());
	    }
	}

}
















