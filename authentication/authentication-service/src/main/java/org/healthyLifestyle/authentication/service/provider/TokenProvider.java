package org.shop.authentication.service.provider;

import org.shop.user.model.User;

public interface TokenProvider<T> {
	T generateToken(User user);

	User decryptToken(String token);
}
