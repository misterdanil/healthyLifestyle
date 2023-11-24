package org.healthylifestyle.communication.service;

import java.util.List;

import org.healthylifestyle.communication.common.dto.AddingUserRequest;
import org.healthylifestyle.communication.common.dto.ChatCreatingRequest;
import org.healthylifestyle.communication.common.dto.ChatUpdatingRequest;
import org.healthylifestyle.communication.model.Chat;
import org.springframework.web.multipart.MultipartFile;

public interface ChatService {
	Chat save(ChatCreatingRequest savingRequest);

	List<Chat> findByTitle(String title);

	Chat update(ChatUpdatingRequest updatingRequest, MultipartFile multipartFile);

	void joinChat(Long chatId);

	void addUser(AddingUserRequest addingRequest);

	void joinByInvitation(Long chatId);

	void leave(Long chatId);

	void remove(Long chatId);

	void attachEvent(Long eventId);
}
