package org.healthylifestyle.notification.service;

import org.healthylifestyle.notification.model.ChatNotification;

public interface ChatNotificationService {
	ChatNotification findByChatIdAndToId(Long chatId, Long toId);
}
