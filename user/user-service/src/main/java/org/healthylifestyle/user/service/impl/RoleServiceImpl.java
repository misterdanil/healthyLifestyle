package org.healthylifestyle.user.service.impl;

import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.repository.RoleRepository;
import org.healthylifestyle.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Role save(String name) {
		Role role = new Role();
		role.setName(name);

		role = roleRepository.save(role);
		
		return role;
	}

}
