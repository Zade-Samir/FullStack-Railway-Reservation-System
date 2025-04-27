package com.capgi.login_service.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.MediaTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgi.login_service.model.AuthRequest;
import com.capgi.login_service.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private AuthService authService;
	
	@InjectMocks
	private AuthController authController;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}
	
	@Test
	void testLogin() throws JsonProcessingException, Exception {
		AuthRequest request = new AuthRequest("user@example.com", "password123");
		String mockToken = "mock-jwt-token";
		
		when(authService.authenticateAndGenerateToken(request)).thenReturn(mockToken);
		
		mockMvc.perform(post("/api/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value(mockToken));
		
		verify(authService).authenticateAndGenerateToken(request);
	}
	
	

}
















