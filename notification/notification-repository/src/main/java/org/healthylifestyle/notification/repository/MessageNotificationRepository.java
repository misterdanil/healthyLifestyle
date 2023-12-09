package org.healthylifestyle.notification.repository;

import org.healthylifestyle.notification.model.MessageNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageNotificationRepository extends JpaRepository<MessageNotification, Long> {

}
