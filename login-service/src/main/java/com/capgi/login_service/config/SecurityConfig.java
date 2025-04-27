package com.capgi.login_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgi.login_service.service.CustomUserDetailsService;
import com.capgi.login_service.service.JwtFilter;

@Configuration
public class SecurityConfig {
	
	private final JwtFilter jwtFilter;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	public SecurityConfig(@Lazy JwtFilter jwtFilter, CustomUserDetailsService customUserDetailsService) {
		this.jwtFilter = jwtFilter;
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/login")
						.permitAll()
						.anyRequest()
						.authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Required to get AuthenticationManager for AuthService
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	// Register your custom UserDetailsService
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

}


















