package org.healthylifestyle.communication.web.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.communication.common.dto.ChatCreatingRequest;
import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ChatController {
	@Autowired
	private ChatService chatService;
	private static final String HOST = "http://localhost:8080";
	private Logger logger = LoggerFactory.getLogger(ChatController.class);

	@PostMapping("/chat")
	public ResponseEntity<ErrorResult> save(@RequestParam("chat") ChatCreatingRequest createRequest,
			@RequestParam("image") MultipartFile file) {
		Chat chat;
		try {
			chat = chatService.save(createRequest, file);
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
	
	
}
