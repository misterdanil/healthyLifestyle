package org.healthylifestyle.communication.common.dto;

import org.healthylifestyle.communication.common.dto.message.MessageDto;

public class ChatDto {
	private Long id;
	private String title;
	private MessageDto lastMessage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MessageDto getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(MessageDto lastMessage) {
		this.lastMessage = lastMessage;
	}

}
