package org.healthylifestyle.event.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.event.common.dto.CompletenessSaveRequest;
import org.healthylifestyle.event.common.dto.EventSaveRequest;
import org.healthylifestyle.event.model.Event;

public interface EventService {
	Event findById(Long id);

	Event save(EventSaveRequest saveRequest) throws ValidationException;

	List<Event> findAllByUser(Long userId, int page);

	List<Event> findAll(int page);

	void invite(Long eventId, Long userId) throws ValidationException;

	void joinByInvitation(Long eventId) throws ValidationException;

	void complete(Long eventId, CompletenessSaveRequest saveRequest) throws ValidationException;

}
