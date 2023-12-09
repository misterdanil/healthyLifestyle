package org.healthylifestyle.notification.service;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.notification.model.MessageNotification;
import org.healthylifestyle.user.model.User;

public interface MessageNotificationService {
	MessageNotification save(Message message, User to);

}
