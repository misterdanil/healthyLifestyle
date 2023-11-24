package org.healthylifestyle.notification.service.impl;

import org.healthylifestyle.notification.model.ChatNotification;
import org.healthylifestyle.notification.repository.ChatNotificationRepository;
import org.healthylifestyle.notification.service.ChatNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationServiceImpl implements ChatNotificationService {
	@Autowired
	private ChatNotificationRepository chatNotificationRepository;

	@Override
	public ChatNotification findByChatIdAndToId(Long chatId, Long toId) {
		return chatNotificationRepository.findByChatIdAndToId(chatId, toId);
	}

}
