package org.healthylifestyle.communication.common.dto;

import jakarta.validation.constraints.NotNull;

public class RemovingChatRequest {
	@NotNull(message = "{chat.remove.id.notnull}")
	private Long chatId;

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

}
