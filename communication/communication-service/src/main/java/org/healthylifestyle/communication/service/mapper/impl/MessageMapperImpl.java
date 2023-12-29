package org.healthylifestyle.communication.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthyLifestyle.authentication.common.mapper.UserMapper;
import org.healthylifestyle.communication.common.dto.message.MessageDto;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.communication.service.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageMapperImpl implements MessageMapper {
	@Autowired
	private UserMapper userMapper;

	@Override
	public MessageDto messageToDto(Message message) {
		if (message == null) {
			return null;
		}
		
		MessageDto dto = new MessageDto();
		dto.setId(message.getId());
		dto.setUser(userMapper.userToDto(message.getChatUser().getUser()));
		dto.setValue(message.getValue());
		dto.setAsnweredMessage(messageToDto(message.getAnsweredMessage()));

		return dto;
	}

	@Override
	public List<MessageDto> messagesToDtos(List<Message> messages) {
		List<MessageDto> dtos = new ArrayList<>();
		messages.forEach(m -> dtos.add(messageToDto(m)));

		return dtos;
	}

}
