package org.healthylifestyle.communication.web.controller;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.communication.common.dto.ChatUserDto;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.service.ChatUserService;
import org.healthylifestyle.communication.service.mapper.ChatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatUserController {
	@Autowired
	private ChatUserService chatUserService;
	@Autowired
	private ChatUserMapper mapper;

	@GetMapping(value = "/chats/{chatId}/users/count", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Object> count(Long chatId) {
		int count;
		try {
			count = chatUserService.count(chatId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok(count);
	}

	@GetMapping("/chats/{chatId}/users")
	public ResponseEntity<Object> findAllChatUsersByChatId(Long chatId) {
		List<ChatUser> chatUsers;
		try {
			chatUsers = chatUserService.findAllChatUsersByChatId(chatId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		List<ChatUserDto> dtos = mapper.chatUsersToDtos(chatUsers);

		return ResponseEntity.ok(dtos);
	}
}
