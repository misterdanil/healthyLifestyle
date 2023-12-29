package org.healthylifestyle.event.web.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.event.common.dto.CompletenessSaveRequest;
import org.healthylifestyle.event.common.dto.EventDto;
import org.healthylifestyle.event.common.dto.EventSaveRequest;
import org.healthylifestyle.event.model.Event;
import org.healthylifestyle.event.service.EventService;
import org.healthylifestyle.event.service.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {
	@Autowired
	private EventService eventService;
	@Autowired
	private EventMapper mapper;

	@PostMapping("/event")
	public ResponseEntity<ErrorResult> createEvent(@RequestBody EventSaveRequest saveRequest) {
		Event event;
		try {
			event = eventService.save(saveRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		try {
			return ResponseEntity.created(new URI("http://localhost:8080/event/" + event.getId())).build();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Exception occurred while creating event. The URI isn't valid", e);
		}
	}

	@GetMapping("/user/{userId}/events")
	public ResponseEntity<List<EventDto>> findAllByUser(@PathVariable Long userId,
			@RequestParam(defaultValue = "1") int page) {
		List<Event> events = eventService.findAllByUser(userId, page);

		List<EventDto> dtos = mapper.eventsToDtos(events);

		return ResponseEntity.ok(dtos);
	}

	@PostMapping("/event/{id}/invitation/{userId}")
	public ResponseEntity<ErrorResult> invite(@PathVariable Long id, @PathVariable Long userId) {
		try {
			eventService.invite(id, userId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/event/{id}/membership")
	public ResponseEntity<ErrorResult> join(@PathVariable Long id) {
		try {
			eventService.joinByInvitation(id);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/event/{id}/complete")
	public ResponseEntity<ErrorResult> complete(@PathVariable Long eventId,
			@RequestBody CompletenessSaveRequest saveRequest) {
		try {
			eventService.complete(eventId, saveRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

}
