package org.healthyLifestyle.authentication.service.provider.impl;

import java.security.Key;

import org.healthyLifestyle.authentication.service.provider.TokenProvider;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
@Scope("prototype")
public abstract class JwtProvider<T> implements TokenProvider<T> {
	private Long expirationTimeMillis;
	private String secretKey;
	private String algorithm;
	@Autowired
	private UserService userService;

	public String getSecretKey() {
		return secretKey;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public Long getExpirationTimeMillis() {
		return expirationTimeMillis;
	}

	public void setExpirationTimeMillis(Long expirationTimeMillis) {
		this.expirationTimeMillis = expirationTimeMillis;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public User decryptToken(String token) {
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
		Jws<Claims> jws;
		try {
			jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			return null;
		}
		String id = jws.getBody().getSubject();
		User user = userService.findById(Long.parseLong(id));

		return user;
	}

}
