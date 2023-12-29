package org.healthylifestyle.user.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.user.common.dto.ParameterTypeDto;
import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;
import org.healthylifestyle.user.service.mapper.MeasureMapper;
import org.healthylifestyle.user.service.mapper.ParameterTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParameterTypeMapperImpl implements ParameterTypeMapper {
	@Autowired
	private MeasureMapper measureMapper;

	@Override
	public ParameterTypeDto parameterTypeToDto(ParameterType pt) {
		ParameterTypeDto dto = new ParameterTypeDto();
		dto.setId(pt.getId());
		dto.setTitle(pt.getOriginalTitle());
		dto.setMeasure(measureMapper.measureToDto(pt.getMeasure()));

		return dto;
	}

	@Override
	public List<ParameterTypeDto> parameterTypesToDtos(List<ParameterType> parameterTypes) {
		List<ParameterTypeDto> dtos = new ArrayList<>();
		parameterTypes.forEach(pt -> dtos.add(parameterTypeToDto(pt)));

		return dtos;
	}

}
