package org.healthylifestyle.communication.service;

import java.util.List;

import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.user.model.Role;

public interface ChatUserService {
	List<Role> findRolesByUsername(String username);

	int countChatUsersByIdIn(Long chatId, List<Long> ids);

	List<ChatUser> findAllByIds(List<Long> ids);
}
