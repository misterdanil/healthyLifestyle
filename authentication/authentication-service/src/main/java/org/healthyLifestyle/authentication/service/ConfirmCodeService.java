package org.healthyLifestyle.authentication.service;

import org.healthyLifestyle.authentication.model.ConfirmCode;
import org.healthylifestyle.user.model.User;

public interface ConfirmCodeService {
	ConfirmCode findByUserId(Long userId);

	ConfirmCode save(User user);
	
	void delete(ConfirmCode code);
}
