package org.healthyLifestyle.authentication.service.provider;

import org.healthylifestyle.user.model.User;

public interface TokenProvider<T> {
	T generateToken(User user);

	User decryptToken(String token);
}
