package org.healthylifestyle.communication.common.dto.message;

import org.healthylifestyle.user.common.dto.UserDto;

public class MessageDto {
	private Long id;
	private String value;
	private MessageDto asnweredMessage;
	private UserDto user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MessageDto getAsnweredMessage() {
		return asnweredMessage;
	}

	public void setAsnweredMessage(MessageDto asnweredMessage) {
		this.asnweredMessage = asnweredMessage;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
