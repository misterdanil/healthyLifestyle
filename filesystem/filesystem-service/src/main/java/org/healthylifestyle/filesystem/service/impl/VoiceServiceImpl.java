package org.healthylifestyle.filesystem.service.impl;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Voice;
import org.healthylifestyle.filesystem.repository.VoiceRepository;
import org.healthylifestyle.filesystem.service.VoiceService;
import org.healthylifestyle.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoiceServiceImpl implements VoiceService {
	@Autowired
	private VoiceRepository voiceRepository;

	@Override
	public List<Voice> findAllByIdIn(List<Long> ids) {
		return voiceRepository.findAllByIdIn(ids);
	}

	@Override
	public void deleteByMessage(List<Long> ids, Message message, User user) {
		voiceRepository.deleteByMessage(ids, message, user);
	}
	
	
}
