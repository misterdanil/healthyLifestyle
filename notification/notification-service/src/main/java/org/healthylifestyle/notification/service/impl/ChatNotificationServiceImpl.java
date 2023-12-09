package org.healthylifestyle.notification.service.impl;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.notification.model.ChatNotification;
import org.healthylifestyle.notification.repository.ChatNotificationRepository;
import org.healthylifestyle.notification.service.ChatNotificationService;
import org.healthylifestyle.notification.service.dto.ChatNotificationDto;
import org.healthylifestyle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationServiceImpl implements ChatNotificationService {
	@Autowired
	private ChatNotificationRepository chatNotificationRepository;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ChatNotification findByChatIdAndToId(Long chatId, Long toId) {
		return chatNotificationRepository.findByChatIdAndToId(chatId, toId);
	}

	@Override
	public ChatNotification save(Chat chat, User from, User to) {
		ChatNotification chatNotification = new ChatNotification();
		chatNotification.setChat(chat);
		chatNotification.setFrom(from);
		chatNotification.setTo(to);

		chatNotification = chatNotificationRepository.save(chatNotification);

		ChatNotificationDto cnd = new ChatNotificationDto();
		cnd.setChatId(chat.getId());
		cnd.setTitle(chat.getTitle());
		cnd.setFirstName(from.getFirstName());
		cnd.setLastName(from.getLastName());

		simpMessagingTemplate.convertAndSend("/attach/notification/user/" + to.getId(), cnd);

		return chatNotification;
	}

}
