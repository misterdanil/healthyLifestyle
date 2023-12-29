package org.healthylifestyle.user.service.mapper;

import java.util.List;

import org.healthylifestyle.user.common.dto.ParameterDto;
import org.healthylifestyle.user.model.lifestyle.healthy.Parameter;

public interface ParameterMapper {
	ParameterDto parameterToDto(Parameter parameter);

	List<ParameterDto> parametersToDtos(List<Parameter> parameters);
}
