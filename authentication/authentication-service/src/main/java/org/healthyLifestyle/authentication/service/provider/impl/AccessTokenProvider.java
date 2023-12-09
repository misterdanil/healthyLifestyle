package org.healthyLifestyle.authentication.service.provider.impl;

import java.time.Instant;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.healthyLifestyle.authentication.common.dto.AccessToken;
import org.healthylifestyle.user.model.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class AccessTokenProvider extends JwtProvider<AccessToken> {

	@Override
	public AccessToken generateToken(User user) {
		Date expDate = Date.from(Instant.now().plusMillis(getExpirationTimeMillis()));

		String token = Jwts.builder().setSubject(String.valueOf(user.getId())).setIssuedAt(new Date())
				.setExpiration(expDate).signWith(new SecretKeySpec(getSecretKey().getBytes(), getAlgorithm()))
				.compact();

		AccessToken accessToken = new AccessToken(token, new Date(), expDate);

		return accessToken;
	}

}
