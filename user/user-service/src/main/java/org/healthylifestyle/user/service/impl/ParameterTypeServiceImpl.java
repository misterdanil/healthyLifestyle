package org.healthylifestyle.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;
import org.healthylifestyle.user.repository.ParameterTypeRepository;
import org.healthylifestyle.user.service.ParameterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterTypeServiceImpl implements ParameterTypeService {
	@Autowired
	private ParameterTypeRepository parameterTypeRepository;

	@Override
	public ParameterType findById(Long id) {
		return parameterTypeRepository.findById(id).orElse(null);
	}

	@Override
	public List<ParameterType> findAll() {
		List<ParameterType> parameterTypes = new ArrayList<>();
		parameterTypeRepository.findAll().forEach(parameterType -> parameterTypes.add(parameterType));

		return parameterTypes;
	}

}
