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
import org.healthylifestyle.user.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface ChatService {
	Chat save(ChatCreatingRequest savingRequest, MultipartFile image) throws ValidationException;

	List<Chat> findByTitle(String title);

	Chat findById(Long id) throws ValidationException;

	Chat update(ChatUpdatingRequest updatingRequest, MultipartFile image) throws ValidationException;

	void joinChat(JoiningChatRequest joiningRequest) throws ValidationException;

	void addUser(AddingUserRequest addingRequest) throws ValidationException;

	void joinByInvitation(JoiningChatRequest joiningRequest) throws ValidationException;

	void leave(LeavingChatRequest leavingRequest) throws ValidationException;

	void remove(RemovingChatRequest removingRequest) throws ValidationException;

	void attachEvent(AttachEventRequest attachRequest) throws ValidationException;

	boolean isMember(Chat chat, User user);
	
	boolean isMember(Long chatId, Long userId);

	boolean isAdmin(Chat chat, User user);

	boolean isOwner(Chat chat, User user);
	
	Chat findByMessage(Long messageId);
}
