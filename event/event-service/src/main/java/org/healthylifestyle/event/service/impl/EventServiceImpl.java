package org.healthylifestyle.event.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.healthylifestyle.common.error.BindingResultFactory;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.event.common.dto.CompletenessSaveRequest;
import org.healthylifestyle.event.common.dto.EventSaveRequest;
import org.healthylifestyle.event.model.Completeness;
import org.healthylifestyle.event.model.Event;
import org.healthylifestyle.event.repository.EventRepository;
import org.healthylifestyle.event.service.CompletenessService;
import org.healthylifestyle.event.service.EventService;
import org.healthylifestyle.notification.model.EventNotification;
import org.healthylifestyle.notification.service.EventNotificationService;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private Validator validator;
	@Autowired
	private UserService userService;
	@Autowired
	private EventNotificationService eventNotificationService;
	@Autowired
	private CompletenessService completenessService;

	private static final int MAX = 50;

	private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	@Override
	public Event findById(Long id) {
		return eventRepository.findById(id).get();
	}

	@Override
	public Event save(EventSaveRequest saveRequest) throws ValidationException {
		logger.debug("Start saving event");

		BindingResult validationResult = BindingResultFactory.getInstance(saveRequest, "eventSaveRequest", validator);

		ErrorParser.checkErrors(validationResult, "Exception occurred while saving event. Its not valid",
				Type.BAD_REQUEST);

		User user = userService
				.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

		Event event = new Event();
		event.setDescription(saveRequest.getDescription());

		String place = saveRequest.getPlace();
		if (place != null && !place.isEmpty()) {
			event.setPlace(place);
		}

		logger.debug("Creating completeness for creator");
		event.addUser(user);
		Completeness completeness = new Completeness();
		completeness.setEvent(event);
		completeness.setUser(user);
		event.addCompletness(completeness);

		event = eventRepository.save(event);

		logger.debug("The event has been created");

		return event;
	}

	@Override
	public List<Event> findAllByUser(Long userId, int page) {
		return eventRepository.findAllByUser(userId, PageRequest.of(page - 1, MAX));
	}

	@Override
	public List<Event> findAll(int page) {
		return eventRepository.findAll(PageRequest.of(page - 1, MAX));
	}

	@Override
	public void invite(Long eventId, Long userId) throws ValidationException {
		logger.debug("Start inviting user " + userId + " to event " + eventId);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(auth.getName()));

		logger.debug("Check if member");
		if (!eventRepository.isMember(eventId, user.getId())) {
			throw new ValidationException("Exception occurred while checking is member. Its not a member", null,
					Type.FORBIDDEN);
		}

		logger.debug("Check event for existence");
		Event event = eventRepository.findById(eventId).orElse(null);
		if (event == null) {
			throw new ValidationException("Exception occurred while checking event for existence. It doesnt exist",
					null, Type.NOT_FOUND);
		}

		logger.debug("Check user for existence");
		User toUser = userService.findById(userId);
		if (toUser == null) {
			throw new ValidationException("Exception occurred while checking user for existence", null, Type.NOT_FOUND);
		}

		eventNotificationService.save(event, user, toUser);

		logger.debug("Invitation has been saved");
	}

	@Override
	public void joinByInvitation(Long eventId) throws ValidationException {
		logger.debug("Joining event " + eventId + " by invitation");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(auth.getName()));

		EventNotification eventNotification = eventNotificationService.findByEventAndTo(eventId, user.getId());
		logger.debug("Trying to get invitation by id");
		if (eventNotification == null) {
			logger.debug("It doesn't exist");
			BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "event");
			result.reject("invitation.notExist", "Вас не приглашали на данное мероприятие");
			throw new ValidationException("Exception occurred while joining by invitation. It doesn't exist", result,
					Type.NOT_FOUND);
		}

		Event event = eventRepository.findById(eventId).get();

		event.addUser(user);

		logger.debug("User has been added");

		Completeness completeness = new Completeness();
		completeness.setEvent(event);
		completeness.setUser(user);
		event.addCompletness(completeness);

		eventRepository.save(event);

		eventNotificationService.delete(eventNotification);
	}

	@Override
	public void complete(Long eventId, CompletenessSaveRequest saveRequest) throws ValidationException {
		logger.debug("Start completing event");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(auth.getName()));

		Completeness completeness = completenessService.findByEventAndUser(eventId, user.getId());
		if (completeness == null) {
			throw new ValidationException("Exception occurred while completing event. The event doesn't exist", null,
					Type.NOT_FOUND);
		}

		completeness.setCompleted(true);

		String description = saveRequest.getDescription();
		if (description != null && !description.isEmpty()) {
			completeness.setDescription(description);
		}

		completenessService.save(completeness);

		logger.debug("The event has been completed");
	}

}
