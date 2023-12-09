package org.healthylifestyle.user.service.impl;

import java.util.List;

import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.user.common.dto.ParameterSaveRequest;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.model.lifestyle.healthy.ClassType;
import org.healthylifestyle.user.model.lifestyle.healthy.Healthy;
import org.healthylifestyle.user.model.lifestyle.healthy.Parameter;
import org.healthylifestyle.user.model.lifestyle.healthy.ParameterType;
import org.healthylifestyle.user.model.lifestyle.healthy.Status;
import org.healthylifestyle.user.repository.ParameterRepository;
import org.healthylifestyle.user.service.ParameterService;
import org.healthylifestyle.user.service.ParameterTypeService;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParameterServiceImpl implements ParameterService {
	@Autowired
	private ParameterRepository parameterRepository;
	@Autowired
	private ParameterTypeService parameterTypeService;
	@Autowired
	private UserService userService;

	@Override
	public Parameter save(ParameterSaveRequest saveRequest) throws ValidationException {
		ParameterType parameterType = parameterTypeService.findById(saveRequest.getParameterTypeId());

		if (parameterType == null) {
			throw new ValidationException(null, null, Type.NOT_FOUND);
		}

		ClassType ct = parameterType.getType();

		if (!ct.isType(saveRequest.getValue())) {
			throw new ValidationException(null, null, Type.BAD_REQUEST);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		Healthy healthy = user.getHealthy();
		if (healthy == null) {
			healthy = new Healthy();
		}

		Parameter parameter = parameterRepository.findByUserAndParameterTypeAndStatus(user.getId(),
				parameterType.getId(), Status.ACTUAL);
		if (parameter != null) {
			parameter.setStatus(Status.ARCHIVED);

			parameterRepository.save(parameter);
		}

		Parameter newParameter = new Parameter();

		newParameter.setHealthy(healthy);
		newParameter.setParameterType(parameterType);
		newParameter.setStatus(Status.ACTUAL);
		newParameter.setValue(saveRequest.getValue());

		newParameter = parameterRepository.save(newParameter);

		healthy.getParameters().add(newParameter);

		return newParameter;
	}

	@Override
	public Parameter findByUserAndParameterTypeAndStatus(Long userId, Long parameterTypeId, Status status) {
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
