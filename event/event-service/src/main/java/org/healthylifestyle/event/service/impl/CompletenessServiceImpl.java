package org.healthylifestyle.event.service.impl;

import org.healthylifestyle.event.model.Completeness;
import org.healthylifestyle.event.repository.CompletenessRepository;
import org.healthylifestyle.event.service.CompletenessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompletenessServiceImpl implements CompletenessService {
	@Autowired
	private CompletenessRepository completenessRepository;

	@Override
	public Completeness findByEventAndUser(Long eventId, Long userId) {
		return completenessRepository.findByEventAndUser(eventId, userId);
	}

	@Override
	public Completeness save(Completeness completeness) {
		return completenessRepository.save(completeness);
	}

}
