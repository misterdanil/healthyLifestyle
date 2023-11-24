package org.healthylifestyle.communication.service.impl;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.repository.ChatUserRepository;
import org.healthylifestyle.communication.service.ChatUserService;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatUserServiceImpl implements ChatUserService {
	@Autowired
	private ChatUserRepository chatUserRepository;

	@Override
	public List<Role> findRolesByChatIdAndUserId(Long chatId, Long userId) {
		return chatUserRepository.findRolesByChatIdAndUserId(chatId, userId);
	}

	@Override
	public int countChatUsersByIdIn(Long chatId, List<Long> ids) {
		return chatUserRepository.countChatUsersByIdIn(chatId, ids);
	}

	@Override
	public boolean existsMember(Long chatId, Long userId) {
		return chatUserRepository.existsMember(chatId, userId);
	}

	@Override
	public List<ChatUser> findAllByIds(List<Long> ids) {
		return chatUserRepository.findAllByIds(ids);
	}

	@Override
	public List<ChatUser> findByChat(Chat chat) {
		return chatUserRepository.findByChat(chat);
	}

	@Override
	public void deleteByUserAndChat(User user, Chat chat) {
		chatUserRepository.deleteByUserAndChat(user, chat);
	}

}
