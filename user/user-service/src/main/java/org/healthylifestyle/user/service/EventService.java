package org.healthylifestyle.user.service;

import org.healthylifestyle.user.model.lifestyle.healthy.event.Event;

public interface EventService {
	Event findById(Long id);
}
