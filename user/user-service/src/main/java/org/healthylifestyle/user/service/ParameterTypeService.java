package org.healthylifestyle.user.service;

import java.util.List;

import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;

public interface ParameterTypeService {
	ParameterType findById(Long id);

	List<ParameterType> findAll();

}
