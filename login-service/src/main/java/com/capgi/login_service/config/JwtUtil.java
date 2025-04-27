package com.capgi.login_service.config;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


//this will generate JWT token, extract username from token, validate token

@Component
public class JwtUtil {

	//creating the token
	public String generateToken(String email) {
		
		return Jwts.builder()
				.subject(email)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(getKey(), Jwts.SIG.HS256)
				.compact();
	}
	
	 private final String SECRET_KEY = "mySuperSecureSecretKeyThatIsAtLeast32Chars";
	 
	//token req 'getkey' so creating it to use above
	 private SecretKey getKey() {
		 return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	 }
	 
	 
	 
//Extract email (username) from token
	 public String extractUsername(String token) {
		 return extractClaim(token, Claims::getSubject);
	 }
	 
	 
	 // Generic claim extractor
	 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		 final Claims claims = extractAllClaims(token);
		 return claimsResolver.apply(claims);
	 }

	 //Parse all claims
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	
	
//Validate token with email
	public Boolean validateToken(String token, String email) {
		final String extractedEmail = extractUsername(token);
		return (extractedEmail.equals(email) && !isTokenExpired(token));
	}

	//checking token expire or not
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	//Extract expiration
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}















