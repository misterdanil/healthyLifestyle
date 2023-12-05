package org.healthylifestyle.filesystem.service;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.user.model.User;

public interface VideoService {
	List<Video> findAllByIdIn(List<Long> ids);

	void deleteByMessage(List<Long> ids, Message message, User user);
}
