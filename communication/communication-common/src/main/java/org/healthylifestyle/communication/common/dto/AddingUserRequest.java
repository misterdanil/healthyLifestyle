package org.healthylifestyle.communication.common.dto;

import java.util.List;

import org.healthylifestyle.user.model.Role;

public class AddingUserRequest {
	private Long userId;
	private List<Role> roles;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
