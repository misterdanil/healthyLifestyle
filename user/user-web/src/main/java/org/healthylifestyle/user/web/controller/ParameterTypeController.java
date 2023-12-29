package org.healthylifestyle.user.web.controller;

import java.util.List;

import org.healthylifestyle.user.common.dto.ParameterTypeDto;
import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;
import org.healthylifestyle.user.service.ParameterTypeService;
import org.healthylifestyle.user.service.mapper.ParameterTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParameterTypeController {
	@Autowired
	private ParameterTypeService parameterTypeService;
	@Autowired
	private ParameterTypeMapper mapper;

	@GetMapping("/parametertypes")
	public ResponseEntity<List<ParameterTypeDto>> findAllParameterTypes() {
		List<ParameterType> parameterTypes = parameterTypeService.findAll();

		List<ParameterTypeDto> dtos = mapper.parameterTypesToDtos(parameterTypes);

		return ResponseEntity.ok(dtos);
	}
}
