package org.healthyLifestyle.authentication.service.generator;

import org.healthylifestyle.user.model.User;

public interface ConfirmCodeGenerator {
	String generate(User user);
}
