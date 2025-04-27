package com.capgi.login_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgi.login_service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

}
