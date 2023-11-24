package org.healthylifestyle.communication.common.dto;

import jakarta.validation.constraints.NotNull;

public class AttachEventRequest {
	@NotNull(message = "chat.attachEvent.eventId.notnull")
	private Long eventId;
	@NotNull(message = "chat.attachEvent.chatId.notnull")
	private Long chatId;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

}
