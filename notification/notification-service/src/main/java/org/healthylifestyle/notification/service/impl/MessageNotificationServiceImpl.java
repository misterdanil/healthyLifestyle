package org.healthylifestyle.notification.service.impl;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.notification.model.MessageNotification;
import org.healthylifestyle.notification.repository.MessageNotificationRepository;
import org.healthylifestyle.notification.service.MessageNotificationService;
import org.healthylifestyle.notification.service.dto.MessageNotificationDto;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageNotificationServiceImpl implements MessageNotificationService {
	@Autowired
	private MessageNotificationRepository messageNotificationRepository;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private UserService userService;

	@Override
	public MessageNotification save(Message message, User to) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		MessageNotification messageNotification = new MessageNotification();
		messageNotification.setMessage(message);
		messageNotification.setFrom(user);
		messageNotification.setTo(to);

		messageNotification = messageNotificationRepository.save(messageNotification);

		MessageNotificationDto mnd = new MessageNotificationDto();
		mnd.setChatId(message.getChat().getId());
		mnd.setFirstName(user.getFirstName());
		mnd.setLastName(user.getLastName());
		mnd.setMessage(message.getValue());

		messagingTemplate.convertAndSend("/attach/notification/" + to.getId(), mnd);

		return messageNotification;
	}

}
