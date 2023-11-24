package org.healthylifestyle.communication.common.dto;

public class AddingUserRequest {
	private Long chatId;
	private Long userId;

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
