package org.healthylifestyle.communication.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthyLifestyle.authentication.common.mapper.UserMapper;
import org.healthylifestyle.communication.common.dto.ChatUserDto;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.service.mapper.ChatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatUserMapperImpl implements ChatUserMapper {
	@Autowired
	private UserMapper userMapper;

	@Override
	public ChatUserDto chatUserToDto(ChatUser chatUser) {
		ChatUserDto dto = new ChatUserDto();
		dto.setId(chatUser.getId());
		dto.setUser(userMapper.userToDto(chatUser.getUser()));

		return null;
	}

	@Override
	public List<ChatUserDto> chatUsersToDtos(List<ChatUser> chatUsers) {
		List<ChatUserDto> dtos = new ArrayList<>();
		chatUsers.forEach(chatUser -> dtos.add(chatUserToDto(chatUser)));

		return dtos;
	}

}
