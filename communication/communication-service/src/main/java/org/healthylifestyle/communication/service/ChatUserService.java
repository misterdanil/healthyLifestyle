package org.healthylifestyle.communication.service;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;

public interface ChatUserService {
	List<Role> findRolesByChatIdAndUserId(Long chatId, Long userId);

	int countChatUsersByIdIn(Long chatId, List<Long> ids);

	boolean existsMember(Long chatId, Long userId);

	List<ChatUser> findAllByIds(List<Long> ids);
	
	List<ChatUser> findByChat(Chat chat);

	void deleteByUserAndChat(User user, Chat chat);
}
