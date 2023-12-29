package org.healthylifestyle.user.service.impl;

import org.healthylifestyle.user.model.lifestyle.healthy.Healthy;
import org.healthylifestyle.user.repository.HealthyRepository;
import org.healthylifestyle.user.service.HealthyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthyServiceImpl implements HealthyService {
	@Autowired
	private HealthyRepository healthyRepository;

	@Override
	public Healthy save(Healthy healthy) {
		return healthyRepository.save(healthy);
	}

}
