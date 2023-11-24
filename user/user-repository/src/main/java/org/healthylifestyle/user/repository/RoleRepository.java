package org.healthylifestyle.user.repository;

import org.healthylifestyle.user.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {

	Role findByName(String name);
}
