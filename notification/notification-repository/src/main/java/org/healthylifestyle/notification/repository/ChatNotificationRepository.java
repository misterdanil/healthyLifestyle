package org.healthylifestyle.notification.repository;

import org.healthylifestyle.notification.model.ChatNotification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatNotificationRepository extends CrudRepository<ChatNotification, Long> {
	@Query("select * from ChatNotification cn where cn.chat_id = :chatId and cn.user_to_id = :userId")
	ChatNotification findByChatIdAndToId(Long chatId, Long toId);
}
