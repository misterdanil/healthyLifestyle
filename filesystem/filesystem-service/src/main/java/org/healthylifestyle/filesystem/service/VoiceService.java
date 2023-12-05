package org.healthylifestyle.filesystem.service;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Voice;
import org.healthylifestyle.user.model.User;

public interface VoiceService {
	List<Voice> findAllByIdIn(List<Long> ids);

	void deleteByMessage(List<Long> ids, Message message, User user);
}
