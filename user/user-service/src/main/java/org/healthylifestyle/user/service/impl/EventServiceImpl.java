package org.healthylifestyle.user.service.impl;

import org.healthylifestyle.user.model.lifestyle.healthy.event.Event;
import org.healthylifestyle.user.repository.event.EventRepository;
import org.healthylifestyle.user.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event findById(Long id) {
		return eventRepository.findById(id).get();
	}

}
