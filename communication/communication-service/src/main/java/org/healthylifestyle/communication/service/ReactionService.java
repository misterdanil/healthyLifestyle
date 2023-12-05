package org.healthylifestyle.communication.service;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.communication.model.Reaction;

public interface ReactionService {
	Reaction view(Long messageId) throws ValidationException;
	
	boolean existsByUser(ChatUser chatUser, Message message);
}
