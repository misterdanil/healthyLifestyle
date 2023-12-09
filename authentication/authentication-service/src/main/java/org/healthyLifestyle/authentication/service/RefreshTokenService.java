package org.healthyLifestyle.authentication.service;

import org.healthyLifestyle.authentication.model.RefreshToken;
import org.healthylifestyle.user.model.User;

public interface RefreshTokenService {
	RefreshToken save(RefreshToken refreshToken);

	void removeByUser(User user);
}
