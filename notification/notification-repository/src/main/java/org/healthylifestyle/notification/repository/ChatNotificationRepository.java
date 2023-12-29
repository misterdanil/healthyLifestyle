package org.healthylifestyle.notification.repository;

import org.healthylifestyle.notification.model.ChatNotification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatNotificationRepository extends CrudRepository<ChatNotification, Long> {
	@Query("select cn from ChatNotification cn where cn.chat.id = :chatId and cn.to.id = :toId")
	ChatNotification findByChatIdAndToId(Long chatId, Long toId);
}
