package org.healthylifestyle.user.service;

import java.util.List;

import org.healthylifestyle.user.model.User;

public interface UserService {
	User findById(Long id);

	User findByUsername(String username);

	int countAllByIdIn(List<Long> ids);

	List<User> findAllByIds(List<Long> ids);
}
