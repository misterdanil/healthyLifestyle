package org.shop.authentication.service.provider.impl;

import javax.crypto.spec.SecretKeySpec;

import org.shop.authentication.service.provider.TokenProvider;
import org.shop.user.model.User;
import org.shop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@PropertySource(value = "classpath:access_token.properties")
public abstract class JwtProvider<T> implements TokenProvider<T> {
	@Resource
	private Environment env;
	private Long expirationTimeMillis;
	private String secretKey;
	private String algorithm;
	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		expirationTimeMillis = env.getProperty("exp", Long.class);
		secretKey = env.getProperty("secret_key");
		algorithm = env.getProperty("algorithm");
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public Long getExpirationTimeMillis() {
		return expirationTimeMillis;
	}

	@Override
	public User decryptToken(String token) {
		Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(new SecretKeySpec(secretKey.getBytes(), algorithm)).build()
				.parseClaimsJws(token);
		String id = jws.getBody().getSubject();
		User user = userService.findById(Long.parseLong(id));

		return user;
	}

}
