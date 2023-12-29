package org.healthylifestyle.communication.web.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.communication.common.dto.AddingUserRequest;
import org.healthylifestyle.communication.common.dto.AttachEventRequest;
import org.healthylifestyle.communication.common.dto.ChatCreatingRequest;
import org.healthylifestyle.communication.common.dto.ChatDto;
import org.healthylifestyle.communication.common.dto.ChatUpdatingRequest;
import org.healthylifestyle.communication.common.dto.JoiningChatRequest;
import org.healthylifestyle.communication.common.dto.LeavingChatRequest;
import org.healthylifestyle.communication.common.dto.RemovingChatRequest;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.service.ChatService;
import org.healthylifestyle.communication.service.mapper.ChatMapper;
import org.healthylifestyle.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
	@Autowired
	private ChatService chatService;
	@Autowired
	private ChatMapper mapper;
	@Autowired
	private UserService userService;
	private static final String HOST = "http://localhost:8080";
	private Logger logger = LoggerFactory.getLogger(ChatController.class);

	@PostMapping(value = "/chat")
	public ResponseEntity<ErrorResult> save(@RequestBody ChatCreatingRequest createRequest) {
		Chat chat;
		try {
			chat = chatService.save(createRequest);
		} catch (ValidationException e) {
			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		try {
			return ResponseEntity.created(new URI(HOST + "/chat/" + chat.getId())).build();
		} catch (URISyntaxException e) {
			logger.error("Exception occurred while saving chat. Couldn't form chat uri location", e);

			return ResponseEntity.status(201).build();
		}
	}

	@GetMapping("/chat/{title}")
	public ResponseEntity<Object> findByTitle(@PathVariable String title) {
		List<Chat> chats = chatService.findByTitle(title);

		List<ChatDto> dtos = mapper.chatsToDtos(chats);

		return ResponseEntity.ok(dtos);
	}

	@PutMapping("/chat/{id}")
	public ResponseEntity<ErrorResult> update(@RequestBody ChatUpdatingRequest updateRequest, @PathVariable Long id) {
		try {
			chatService.update(updateRequest, id);
		} catch (ValidationException e) {
			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		try {
			return ResponseEntity.created(new URI(HOST + "/chat/" + id)).build();
		} catch (URISyntaxException e) {
			logger.error("Exception occurred while updating chat. Couldn't form chat uri location", e);

			return ResponseEntity.status(201).build();
		}
	}

	@PostMapping("/chat/{id}/membership")
	public ResponseEntity<ErrorResult> join(@RequestBody JoiningChatRequest joinRequest, Long id) {
		try {
			chatService.joinChat(joinRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/chat/{chatId}/user/{userId}")
	public ResponseEntity<ErrorResult> addUser(@PathVariable Long chatId, @PathVariable Long userId) {
		AddingUserRequest addRequest = new AddingUserRequest();
		addRequest.setChatId(chatId);
		addRequest.setUserId(userId);

		try {
			chatService.addUser(addRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/chat/{chatId}/user/{userId}/invitation")
	public ResponseEntity<ErrorResult> inviteUser(@PathVariable Long chatId, @PathVariable Long userId) {
		try {
			chatService.inviteUser(chatId, userId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/chat/{chatId}/leaving")
	public ResponseEntity<ErrorResult> leave(@PathVariable Long chatId) {
		LeavingChatRequest leaveRequest = new LeavingChatRequest();
		leaveRequest.setChatId(chatId);

		try {
			chatService.leave(leaveRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/chat/{chatId}")
	public ResponseEntity<ErrorResult> delete(@PathVariable Long chatId) {
		RemovingChatRequest removeRequest = new RemovingChatRequest();
		removeRequest.setChatId(chatId);

		try {
			chatService.remove(removeRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PutMapping("/chat/{chatId}/event/{eventId}")
	public ResponseEntity<ErrorResult> attachEvent(@PathVariable Long chatId, @PathVariable Long eventId) {
		AttachEventRequest attachRequest = new AttachEventRequest();
		attachRequest.setChatId(chatId);
		attachRequest.setEventId(eventId);

		try {
			chatService.attachEvent(attachRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/chats")
	public ResponseEntity<Object> getAllChats(@RequestParam int page) {
		List<Chat> chats = chatService.findAllOwn(page);

		List<ChatDto> dtos = mapper.chatsToDtos(chats);

		return ResponseEntity.ok(dtos);
	}
}
