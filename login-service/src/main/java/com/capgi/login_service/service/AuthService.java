package com.capgi.login_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.capgi.login_service.config.JwtUtil;
import com.capgi.login_service.model.AuthRequest;

import lombok.RequiredArgsConstructor;


//it validate the user credentials as request from the controller
@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final JwtUtil jwtUtil;
	
	@Autowired
	private WebClient.Builder webClientBuilder; //allow one service to talk to another like RestTemplate

	public String authenticateAndGenerateToken(AuthRequest request) {
		
		try {
			String registeredUserEmail = webClientBuilder.build() //create fresh web client
										.post() //posting the request to client
										.uri("http://REGISTRATION-SERVICE/api/validate") //URL which we're calling
										.bodyValue(request) //sending email and password with request
										.retrieve() //getting response after sending the above request
										.bodyToMono(String.class) //converting response into string
										.block(); //webclient is reactive (non-blocking) so converting into normal java object.
			
			if (registeredUserEmail != null && registeredUserEmail.equals(request.getEmail())) {
				
				return jwtUtil.generateToken(request.getEmail());
			}
			else {
				throw new RuntimeException("Invalid Credentials");
			}
		}
		catch (WebClientResponseException e) {
			System.err.println("Status Code: " + e.getStatusCode());
		    System.err.println("Response Body: " + e.getResponseBodyAsString());
			throw new RuntimeException("User validation failed: " + e.getMessage());
		}
	}

}

















