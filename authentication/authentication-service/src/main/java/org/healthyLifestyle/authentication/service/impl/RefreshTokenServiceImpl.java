package org.shop.authentication.service.impl;

import org.shop.authentication.model.RefreshToken;
import org.shop.authentication.repository.RefreshTokenRepository;
import org.shop.authentication.service.RefreshTokenService;
import org.shop.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Override
	public RefreshToken save(RefreshToken refreshToken) {
		return refreshTokenRepository.save(refreshToken);
	}

	@Override
	public void removeByUser(User user) {
		refreshTokenRepository.removeByUser(user);
	}

}
