package org.healthylifestyle.user.repository;

import org.healthylifestyle.user.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByName(String name);
}
