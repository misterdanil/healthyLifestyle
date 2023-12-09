package org.healthylifestyle.notification.service.impl;

import java.util.List;

import org.healthylifestyle.notification.model.Notification;
import org.healthylifestyle.notification.repository.NotificationRepository;
import org.healthylifestyle.notification.service.NotificationService;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private UserService userService;

	private static final int MAX = 50;

	@Override
	public List<Notification> findAll(int page) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		List<Notification> notifications = notificationRepository.findByFrom(user, PageRequest.of(page - 1, MAX));

		return notifications;
	}

	@Override
	public List<Notification> findShort() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		List<Notification> notifications = notificationRepository.findByFrom(user, PageRequest.of(0, 10));

		return notifications;
	}

}
