package org.healthylifestyle.user.service.util;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.user.model.Privilege;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleUtil {
	private RoleUtil() {
	}

	public static List<? extends GrantedAuthority> getAuthorities(User user) {
		List<Role> roles = user.getRoles();
		List<Privilege> privileges = new ArrayList<>();
		List<String> privilegeNames = new ArrayList<>();

		roles.forEach(role -> {
			privilegeNames.add(role.getName());
			privileges.addAll(role.getPrivileges());
		});

		privileges.forEach(privilege -> privilegeNames.add(privilege.getName()));

		List<GrantedAuthority> authorities = new ArrayList<>();
		privilegeNames.forEach(name -> authorities.add(new SimpleGrantedAuthority(name)));

		return authorities;
	}

}
