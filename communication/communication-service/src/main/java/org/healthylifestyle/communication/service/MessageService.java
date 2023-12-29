package org.healthylifestyle.communication.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.communication.common.dto.message.SaveMessageRequest;
import org.healthylifestyle.communication.common.dto.message.UpdateMessageRequest;
import org.healthylifestyle.communication.model.Message;

public interface MessageService {
	Message findById(Long messageId);

	Message save(SaveMessageRequest saveRequest, Long chatId) throws ValidationException;

	List<Message> findByTextIn(Long chatId, String text, int page) throws ValidationException;

	List<Message> findAllByChatId(Long chatId, int page) throws ValidationException;

	void delete(Long messageId) throws ValidationException;

	void update(UpdateMessageRequest updateRequest, Long id) throws ValidationException;

	void addToFavorites(Long messageId) throws ValidationException;

	Message answerMessage(SaveMessageRequest saveRequest, Long id) throws ValidationException;

	List<Message> findFavorites(int page);

	Message findLastByChat(Long chatId);

}