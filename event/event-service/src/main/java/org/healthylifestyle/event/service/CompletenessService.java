package org.healthylifestyle.event.service;

import org.healthylifestyle.event.model.Completeness;

public interface CompletenessService {
	Completeness findByEventAndUser(Long eventId, Long userId);

	Completeness save(Completeness completeness);
}
