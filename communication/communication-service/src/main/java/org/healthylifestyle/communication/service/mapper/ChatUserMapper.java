package org.healthylifestyle.communication.service.mapper;

import java.util.List;

import org.healthylifestyle.communication.common.dto.ChatUserDto;
import org.healthylifestyle.communication.model.ChatUser;

public interface ChatUserMapper {
	ChatUserDto chatUserToDto(ChatUser chatUser);
	
	List<ChatUserDto> chatUsersToDtos(List<ChatUser> chatUsers);
}
