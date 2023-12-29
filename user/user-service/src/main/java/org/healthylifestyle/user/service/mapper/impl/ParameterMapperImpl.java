package org.healthylifestyle.user.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.user.common.dto.ParameterDto;
import org.healthylifestyle.user.model.lifestyle.healthy.Parameter;
import org.healthylifestyle.user.service.mapper.ParameterMapper;
import org.healthylifestyle.user.service.mapper.ParameterTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParameterMapperImpl implements ParameterMapper {
	@Autowired
	private ParameterTypeMapper parameterTypeMapper;

	@Override
	public ParameterDto parameterToDto(Parameter parameter) {
		ParameterDto dto = new ParameterDto();
		dto.setId(parameter.getId());
		dto.setParameterType(parameterTypeMapper.parameterTypeToDto(parameter.getParameterType()));
		dto.setStatus(parameter.getStatus().toString());
		dto.setValue(parameter.getValue());

		return dto;
	}

	@Override
	public List<ParameterDto> parametersToDtos(List<Parameter> parameters) {
		List<ParameterDto> dtos = new ArrayList<>();
		parameters.forEach(pt -> dtos.add(parameterToDto(pt)));

		return dtos;
	}
}
