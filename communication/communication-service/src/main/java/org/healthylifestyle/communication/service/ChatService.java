package org.healthylifestyle.communication.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.communication.common.dto.AddingUserRequest;
import org.healthylifestyle.communication.common.dto.AttachEventRequest;
import org.healthylifestyle.communication.common.dto.ChatCreatingRequest;
import org.healthylifestyle.communication.common.dto.ChatUpdatingRequest;
import org.healthylifestyle.communication.common.dto.JoiningChatRequest;
import org.healthylifestyle.communication.common.dto.LeavingChatRequest;
import org.healthylifestyle.communication.common.dto.RemovingChatRequest;
import org.healthylifestyle.communication.model.Chat;

public interface ChatService {
	Chat save(ChatCreatingRequest savingRequest) throws ValidationException;

	List<Chat> findByTitle(String title);

	Chat findById(Long id) throws ValidationException;

	Chat update(ChatUpdatingRequest updatingRequest, Long id) throws ValidationException;

	void joinChat(JoiningChatRequest joiningRequest) throws ValidationException;

	void addUser(AddingUserRequest addingRequest) throws ValidationException;

	void inviteUser(Long chatId, Long userId) throws ValidationException;

	void joinByInvitation(JoiningChatRequest joiningRequest) throws ValidationException;

	void leave(LeavingChatRequest leavingRequest) throws ValidationException;

	void remove(RemovingChatRequest removingRequest) throws ValidationException;

	void attachEvent(AttachEventRequest attachRequest) throws ValidationException;

	Chat findByMessage(Long messageId);

	List<Chat> findAllOwn(int page);
}
