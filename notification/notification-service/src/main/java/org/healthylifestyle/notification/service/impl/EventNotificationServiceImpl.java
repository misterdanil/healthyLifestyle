package org.healthylifestyle.notification.service.impl;

import org.healthylifestyle.event.model.Event;
import org.healthylifestyle.notification.model.EventNotification;
import org.healthylifestyle.notification.repository.EventNotificationRepository;
import org.healthylifestyle.notification.service.EventNotificationService;
import org.healthylifestyle.notification.service.dto.EventNotificationDto;
import org.healthylifestyle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventNotificationServiceImpl implements EventNotificationService {
	@Autowired
	private EventNotificationRepository eventNotificationRepository;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Override
	public EventNotification save(Event event, User from, User to) {
		EventNotification eventNotification = new EventNotification();

		eventNotification.setEvent(event);
		eventNotification.setFrom(from);
		eventNotification.setTo(to);

		eventNotification = eventNotificationRepository.save(eventNotification);

		EventNotificationDto dto = new EventNotificationDto();
		dto.setEventId(event.getId());
		dto.setFirstName(from.getFirstName());
		dto.setLastName(from.getLastName());
		dto.setDescription(event.getDescription());

		messagingTemplate.convertAndSend("attach/notification/" + from.getId(), dto);

		return eventNotification;
	}

	@Override
	public EventNotification findByEventAndTo(Long eventId, Long userId) {
		return eventNotificationRepository.findByEventAndTo(eventId, userId);
	}

	@Override
	public void delete(EventNotification eventNotification) {
		eventNotificationRepository.delete(eventNotification);
	}

}
