package org.healthylifestyle.communication.common.dto;

import org.healthylifestyle.user.common.dto.UserDto;

public class ChatUserDto {
	private Long id;
	private UserDto user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
