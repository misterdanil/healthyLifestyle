package org.healthylifestyle.user.service.mapper;

import java.util.List;

import org.healthylifestyle.user.common.dto.ParameterTypeDto;
import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;

public interface ParameterTypeMapper {
	ParameterTypeDto parameterTypeToDto(ParameterType pt);

	List<ParameterTypeDto> parameterTypesToDtos(List<ParameterType> parameterTypes);
}
