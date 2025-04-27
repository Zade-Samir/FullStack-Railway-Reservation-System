package com.capgi.login_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
//import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.capgi.login_service.config.JwtUtil;
import com.capgi.login_service.model.AuthRequest;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	
	@Mock
	private JwtUtil jwtUtil;
	
	@Mock
	private WebClient.Builder webClientBuilder;
	
	@Mock
	private WebClient webClient;
	
	@Mock
	private WebClient.RequestBodyUriSpec requestBodyUriSpec;
	
	@Mock
	private WebClient.RequestBodySpec requestBodySpec;
	
	@Mock
	private WebClient.ResponseSpec responseSpec;
	
	@InjectMocks
	private AuthService authService;
	
	private AuthRequest authRequest;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks
		AuthRequest request = new AuthRequest();
		request.setEmail("user@example.com");
		request.setPassword("password123");
	}
	
	@Test
	void testAuthenticateAndGenerateToken_Success() {
		
		AuthRequest request = new AuthRequest("user@example.com", "password123");
		
		// Mocking WebClient chain - as webclient is check into chain way
		when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("http://REGISTRATION-SERVICE/api/validate"))
        						.thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(request)).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("user@example.com"));
		
		// Mock JWT generation
        when(jwtUtil.generateToken("user@example.com")).thenReturn("fake-jwt-token");
        
        // Call the service
        String result = authService.authenticateAndGenerateToken(request);
        
        // check the test cases
        assertEquals("fake-jwt-token", result);
		
	}

}




























