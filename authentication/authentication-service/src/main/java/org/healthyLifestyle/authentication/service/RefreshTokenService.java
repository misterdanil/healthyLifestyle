package org.shop.authentication.service;

import org.shop.authentication.model.RefreshToken;
import org.shop.user.model.User;

public interface RefreshTokenService {
	RefreshToken save(RefreshToken refreshToken);

	void removeByUser(User user);
}
