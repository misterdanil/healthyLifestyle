package org.healthylifestyle.notification.service;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.notification.model.ChatNotification;
import org.healthylifestyle.user.model.User;

public interface ChatNotificationService {
	ChatNotification findByChatIdAndToId(Long chatId, Long toId);

	ChatNotification save(Chat chat, User from, User to);
	
	void delete(ChatNotification chatNotification);
}
