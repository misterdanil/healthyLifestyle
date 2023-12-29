package org.healthylifestyle.notification.service;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.notification.model.MessageNotification;

public interface MessageNotificationService {
	void save(Message message);

}
