package org.healthylifestyle.notification.service;

import java.util.List;

import org.healthylifestyle.notification.model.Notification;

public interface NotificationService {
	List<Notification> findAll(int page);
	
	
	List<Notification> findShort();
}
