package org.healthylifestyle.communication.service.mapper;

import java.util.List;

import org.healthylifestyle.communication.common.dto.message.MessageDto;
import org.healthylifestyle.communication.model.Message;

public interface MessageMapper {
	MessageDto messageToDto(Message message);

	List<MessageDto> messagesToDtos(List<Message> messages);
}
