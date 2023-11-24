package org.shop.authentication.service.generator;

import org.shop.user.model.User;

public interface ConfirmCodeGenerator {
	String generate(User user);
}
