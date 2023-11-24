package org.shop.authentication.service.provider.impl;

import java.time.Instant;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.shop.authentication.model.RefreshToken;
import org.shop.user.model.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class RefreshTokenProvider extends JwtProvider<RefreshToken> {

	@Override
	public RefreshToken generateToken(User user) {
		Date expDate = Date.from(Instant.now().plusMillis(getExpirationTimeMillis()));

		String token = Jwts.builder().setSubject(String.valueOf(user.getId())).setIssuedAt(new Date())
				.setExpiration(expDate).signWith(new SecretKeySpec(getSecretKey().getBytes(), getAlgorithm()))
				.compact();

		RefreshToken refreshToken = new RefreshToken(token, new Date(), expDate, user);

		return refreshToken;
	}

}
