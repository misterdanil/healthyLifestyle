package org.healthylifestyle.communication.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;

public interface ChatUserService {
	ChatUser findByChatAndUser(Long chatId, Long userId);

	List<Role> findRolesByChatIdAndUserId(Long chatId, Long userId);

	int countChatUsersByIdIn(Long chatId, List<Long> ids);

	int count(Long id) throws ValidationException;

	boolean existsMember(Long chatId, Long userId);

	List<ChatUser> findAllByIds(List<Long> ids);

	List<ChatUser> findByChat(Chat chat);

	void deleteByUserAndChat(User user, Chat chat);

	List<ChatUser> findAllChatUsersByChatId(Long chatId) throws ValidationException;

	void update(ChatUser chatUser);
}
