package org.shop.authentication.service;

import org.shop.authentication.model.ConfirmCode;
import org.shop.user.model.User;

public interface ConfirmCodeService {
	ConfirmCode findByUserId(Long userId);

	ConfirmCode save(User user);
}
