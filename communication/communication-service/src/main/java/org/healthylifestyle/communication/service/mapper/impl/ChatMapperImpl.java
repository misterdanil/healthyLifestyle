package org.healthylifestyle.communication.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.communication.common.dto.ChatDto;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.communication.service.MessageService;
import org.healthylifestyle.communication.service.mapper.ChatMapper;
import org.healthylifestyle.communication.service.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMapperImpl implements ChatMapper {
	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private MessageService messageService;

	@Override
	public ChatDto chatToDto(Chat chat) {
		ChatDto dto = new ChatDto();
		dto.setId(chat.getId());
		dto.setTitle(chat.getTitle());

		Message lastMessage = messageService.findLastByChat(chat.getId());

		if (lastMessage != null) {
			dto.setLastMessage(messageMapper.messageToDto(lastMessage));
		}
		
		return dto;
	}

	@Override
	public List<ChatDto> chatsToDtos(List<Chat> chats) {
		List<ChatDto> dtos = new ArrayList<>();

		chats.forEach(chat -> dtos.add(chatToDto(chat)));

		return dtos;
	}

}
