package org.healthylifestyle.user.service;

import org.healthylifestyle.user.model.Role;

public interface RoleService {
	Role findByName(String name);

	void save(String name);
}
