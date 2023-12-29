package org.healthylifestyle.notification.repository;

import org.healthylifestyle.notification.model.EventNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventNotificationRepository extends JpaRepository<EventNotification, Long> {
	@Query("select en from EventNotification en where en.event.id = :eventId and en.to.id = :userId")
	EventNotification findByEventAndTo(Long eventId, Long userId);
}
