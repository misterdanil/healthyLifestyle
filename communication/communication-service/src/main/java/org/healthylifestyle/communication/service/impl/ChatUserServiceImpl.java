package org.healthylifestyle.communication.service.impl;

import java.util.List;

import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.repository.ChatUserRepository;
import org.healthylifestyle.communication.service.ChatUserService;
import org.healthylifestyle.user.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatUserServiceImpl implements ChatUserService {
	@Autowired
	private ChatUserRepository chatUserRepository;

	@Override
	public List<Role> findRolesByUsername(String username) {
		return chatUserRepository.findRolesByUsername(username);
	}

	@Override
	public int countChatUsersByIdIn(Long chatId, List<Long> ids) {
		return chatUserRepository.countChatUsersByIdIn(chatId, ids);
	}

	@Override
	public List<ChatUser> findAllByIds(List<Long> ids) {
		return chatUserRepository.findAllByIds(ids);
	}

}
