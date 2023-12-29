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
	public void save(Message message) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(auth.getName()));

		MessageNotificationDto mnd = new MessageNotificationDto();
		mnd.setChatId(message.getChat().getId());
		mnd.setFirstName(user.getFirstName());
		mnd.setLastName(user.getLastName());
		mnd.setMessage(message.getValue());

		message.getChat().getUsers().forEach(u -> {
			if (!u.getUser().getId().equals(user.getId())) {
				MessageNotification messageNotification = new MessageNotification();
				messageNotification.setMessage(message);
				messageNotification.setFrom(user);
				messageNotification.setTo(u.getUser());

				messageNotification = messageNotificationRepository.save(messageNotification);

				messagingTemplate.convertAndSend("/attach/notification/" + u.getUser().getId(), mnd);
			}
		});

		messagingTemplate.convertAndSend("/chatss/" + message.getChat().getId(), mnd);
	}

}
