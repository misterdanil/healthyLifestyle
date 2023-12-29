package org.healthylifestyle.user.web.controller;

import java.util.List;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.user.common.dto.ParameterDto;
import org.healthylifestyle.user.common.dto.ParameterSaveRequest;
import org.healthylifestyle.user.model.lifestyle.healthy.Parameter;
import org.healthylifestyle.user.model.lifestyle.healthy.Status;
import org.healthylifestyle.user.service.ParameterService;
import org.healthylifestyle.user.service.mapper.ParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParameterController {
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private ParameterMapper mapper;

	@PostMapping("/parameter")
	public ResponseEntity<ErrorResult> save(@RequestBody ParameterSaveRequest saveRequest) {
		try {
			parameterService.save(saveRequest);
		} catch (ValidationException e) {

			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/{userId}/parameters")
	public ResponseEntity<List<ParameterDto>> findByUserAndParameterTypeAndStatus(@PathVariable Long userId,
			@RequestParam(required = false) Long parameterTypeId, @RequestParam Status status) {
		List<Parameter> parameters;
		if (parameterTypeId != null && status != null) {
			parameters = parameterService.findByUserAndParameterTypeAndStatus(userId, parameterTypeId, status);
		} else {
			parameters = parameterService.findAllByUserAndStatus(userId, status);
		}

		List<ParameterDto> dtos = mapper.parametersToDtos(parameters);

		return ResponseEntity.ok(dtos);
	}
}
