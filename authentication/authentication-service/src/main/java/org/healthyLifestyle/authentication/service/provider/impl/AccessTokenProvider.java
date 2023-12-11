package org.healthyLifestyle.authentication.service.provider.impl;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.healthyLifestyle.authentication.common.dto.AccessToken;
import org.healthylifestyle.user.model.User;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Component
@PropertySource(value = "classpath:access_token.properties")
public class AccessTokenProvider extends JwtProvider<AccessToken> {
	@Resource
	private Environment env;

	@PostConstruct
	public void init() {
		setExpirationTimeMillis(env.getProperty("at_exp", Long.class));
		setSecretKey(env.getProperty("at_secret_key"));
		setAlgorithm(env.getProperty("at_algorithm"));
	}

	@Override
	public AccessToken generateToken(User user) {
		Date expDate = Date.from(Instant.now().plusMillis(getExpirationTimeMillis()));

		Key key = Keys.hmacShaKeyFor(getSecretKey().getBytes());

		String token = Jwts.builder().setSubject(String.valueOf(user.getId())).setIssuedAt(new Date())
				.setExpiration(expDate).signWith(key, SignatureAlgorithm.valueOf(getAlgorithm())).compact();

		AccessToken accessToken = new AccessToken(token, new Date(), expDate);

		return accessToken;
	}

}
