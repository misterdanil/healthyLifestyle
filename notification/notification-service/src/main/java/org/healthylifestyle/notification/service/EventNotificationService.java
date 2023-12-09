package org.healthylifestyle.notification.service;

import org.healthylifestyle.event.model.Event;
import org.healthylifestyle.notification.model.EventNotification;
import org.healthylifestyle.user.model.User;

public interface EventNotificationService {
	EventNotification findByEventAndTo(Long eventId, Long userId);

	EventNotification save(Event event, User from, User to);
	
	void delete(EventNotification eventNotification);
}
