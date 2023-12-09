package org.healthyLifestyle.authentication.service.impl;

import org.healthyLifestyle.authentication.model.RefreshToken;
import org.healthyLifestyle.authentication.repository.RefreshTokenRepository;
import org.healthyLifestyle.authentication.service.RefreshTokenService;
import org.healthylifestyle.user.model.User;
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
