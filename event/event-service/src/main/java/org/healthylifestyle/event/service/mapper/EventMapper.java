package org.healthylifestyle.event.service.mapper;

import java.util.List;

import org.healthylifestyle.event.common.dto.EventDto;
import org.healthylifestyle.event.model.Event;

public interface EventMapper {
	EventDto eventToDto(Event event);

	List<EventDto> eventsToDtos(List<Event> events);
}
