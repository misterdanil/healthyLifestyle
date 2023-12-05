package org.healthylifestyle.filesystem.service.impl;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.repository.VideoRepository;
import org.healthylifestyle.filesystem.service.VideoService;
import org.healthylifestyle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	private VideoRepository videoRepository;

	@Override
	public void deleteByMessage(List<Long> ids, Message message, User user) {
		videoRepository.deleteByMessage(ids, message, user);
	}
}
