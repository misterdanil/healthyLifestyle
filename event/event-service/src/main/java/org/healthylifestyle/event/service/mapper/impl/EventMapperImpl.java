package org.healthylifestyle.event.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.event.common.dto.EventDto;
import org.healthylifestyle.event.model.Event;
import org.healthylifestyle.event.service.mapper.EventMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements EventMapper {

	@Override
	public EventDto eventToDto(Event event) {
		EventDto dto = new EventDto();
		dto.setId(event.getId());
		dto.setPlace(event.getPlace());
		dto.setDescription(event.getDescription());

		return dto;
	}

	@Override
	public List<EventDto> eventsToDtos(List<Event> events) {
		List<EventDto> dtos = new ArrayList<>();
		events.forEach(e -> dtos.add(eventToDto(e)));
		
		return dtos;
	}

}
