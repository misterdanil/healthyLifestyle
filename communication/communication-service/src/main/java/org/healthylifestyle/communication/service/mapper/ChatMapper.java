package org.healthylifestyle.communication.service.mapper;

import java.util.List;

import org.healthylifestyle.communication.common.dto.ChatDto;
import org.healthylifestyle.communication.model.Chat;

public interface ChatMapper {
	ChatDto chatToDto(Chat chat);

	List<ChatDto> chatsToDtos(List<Chat> chats);
}
