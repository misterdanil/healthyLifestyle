package org.healthylifestyle.communication.web.controller;

import java.util.List;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.communication.common.dto.message.MessageDto;
import org.healthylifestyle.communication.common.dto.message.SaveMessageRequest;
import org.healthylifestyle.communication.common.dto.message.UpdateMessageRequest;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.communication.service.MessageService;
import org.healthylifestyle.communication.service.mapper.MessageMapper;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageMapper mapper;
	@Autowired
	private SimpMessagingTemplate t;
	@Autowired
	private UserService us;

	@PostMapping("/chat/{chatId}/message")
	public ResponseEntity<ErrorResult> save(@RequestBody SaveMessageRequest saveRequest, @PathVariable Long chatId) {
		try {
			messageService.save(saveRequest, chatId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/chat/{chatId}/messages/{text}")
	public ResponseEntity<Object> findByTextIn(@PathVariable Long chatId, @PathVariable String text,
			@RequestParam int page) {
		List<Message> messages;
		try {
			messages = messageService.findByTextIn(chatId, text, page);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		List<MessageDto> dtos = mapper.messagesToDtos(messages);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/chat/{chatId}/messages")
	public ResponseEntity<Object> findAllByChatId(@PathVariable Long chatId, @RequestParam int page) {
		List<Message> messages;
		try {
			messages = messageService.findAllByChatId(chatId, page);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		List<MessageDto> dtos = mapper.messagesToDtos(messages);

		return ResponseEntity.ok(dtos);
	}

	@DeleteMapping("/chat/*/message/{messageId}")
	public ResponseEntity<ErrorResult> delete(@PathVariable Long messageId) {
		try {
			messageService.delete(messageId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/chat/*/message/{messageId}")
	public ResponseEntity<ErrorResult> update(@RequestBody UpdateMessageRequest updateRequest,
			@PathVariable Long messageId) {
		try {
			messageService.update(updateRequest, messageId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/chat/*/message/{messageId}/favorite")
	public ResponseEntity<ErrorResult> addToFavorites(@PathVariable Long messageId) {
		try {
			messageService.addToFavorites(messageId);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/chat/*/message/{id}")
	public ResponseEntity<ErrorResult> answerMessage(@RequestBody SaveMessageRequest saveRequest,
			@PathVariable Long id) {
		try {
			messageService.answerMessage(saveRequest, id);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/messages/favorites")
	public ResponseEntity<Object> findFavorites(@RequestParam int page) {
		List<Message> messages;

		messages = messageService.findFavorites(page);

		List<MessageDto> dtos = mapper.messagesToDtos(messages);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/testsocket")
	@ResponseBody
	public String testSocket() {
		User user = us.findById(1L);
		t.convertAndSendToUser(String.valueOf(user.getId()), "/chatss", "Hello");
		return "what";
	}
}
