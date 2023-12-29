package org.healthylifestyle.user.service.impl;

import java.util.List;

import org.healthylifestyle.common.error.BindingResultFactory;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.user.common.dto.ParameterSaveRequest;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.model.lifestyle.healthy.ClassType;
import org.healthylifestyle.user.model.lifestyle.healthy.Healthy;
import org.healthylifestyle.user.model.lifestyle.healthy.Parameter;
import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;
import org.healthylifestyle.user.model.lifestyle.healthy.Status;
import org.healthylifestyle.user.repository.ParameterRepository;
import org.healthylifestyle.user.service.HealthyService;
import org.healthylifestyle.user.service.ParameterService;
import org.healthylifestyle.user.service.ParameterTypeService;
import org.healthylifestyle.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Service
public class ParameterServiceImpl implements ParameterService {
	@Autowired
	private ParameterRepository parameterRepository;
	@Autowired
	private ParameterTypeService parameterTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private HealthyService healthyService;
	@Autowired
	private Validator validator;

	private static final Logger logger = LoggerFactory.getLogger(ParameterServiceImpl.class);

	@Override
	public Parameter save(ParameterSaveRequest saveRequest) throws ValidationException {
		logger.debug("Start saving parameter");

		BindingResult validationResult = BindingResultFactory.getInstance(saveRequest, "parameterSaveRequest",
				validator);

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while saving a parameter. The request isn't valid", Type.BAD_REQUEST);

		ParameterType parameterType = parameterTypeService.findById(saveRequest.getParameterTypeId());

		logger.debug("Checking parameter type for the existence");
		if (parameterType == null) {
			validationResult.rejectValue("parameterTypeId", "parameter.save.parameterTypeId.notExist",
					"Не существует параметра с таким идентификатором " + saveRequest.getParameterTypeId());
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while checking parameter type for existence. The parameter type doesn't exist",
				Type.NOT_FOUND);

		ClassType ct = parameterType.getType();

		logger.debug("Checking parameter value for correct");
		if (!ct.isType(saveRequest.getValue())) {
			validationResult.rejectValue("value", "parameter.save.value.invalid",
					"The value " + saveRequest.getValue() + " isn't valid");
		}

		ErrorParser.checkErrors(validationResult,
				"Exception occurred while checking value for correct. The parameter type doesn't exist",
				Type.BAD_REQUEST);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findById(Long.valueOf(auth.getName()));

		Healthy healthy = user.getHealthy();
		if (healthy == null) {
			healthy = new Healthy();
			healthy.setUser(user);
			healthyService.save(healthy);
		}

		Parameter parameter = parameterRepository.findActualByUserAndParameterType(user.getId(), parameterType.getId());
		logger.debug("Searching previous parameter to archive it");
		if (parameter != null) {
			parameter.setStatus(Status.ARCHIVED);

			parameterRepository.save(parameter);
		}

		logger.debug("Creating new parameter");
		Parameter newParameter = new Parameter();

		newParameter.setHealthy(healthy);
		newParameter.setParameterType(parameterType);
		newParameter.setStatus(Status.ACTUAL);
		newParameter.setValue(saveRequest.getValue());

		healthy.getParameters().add(newParameter);

		newParameter = parameterRepository.save(newParameter);

		logger.debug("The parameter has been saved");
		
		return newParameter;
	}

	@Override
	public List<Parameter> findByUserAndParameterTypeAndStatus(Long userId, Long parameterTypeId, Status status) {
		return parameterRepository.findByUserAndParameterTypeAndStatus(userId, parameterTypeId, status);
	}

	@Override
	public List<Parameter> findAllByUserAndStatus(Long userId, Status status) {
		return parameterRepository.findAllByUserAndStatus(userId, status);
	}

	@Override
	public List<Parameter> findByParameterTypeAndStatus(Long parameterTypeId, Status status) {
		return parameterRepository.findByParameterTypeAndStatus(parameterTypeId, status);
	}

}
