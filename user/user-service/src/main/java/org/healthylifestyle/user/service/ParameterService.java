package org.healthylifestyle.user.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.user.common.dto.ParameterSaveRequest;
import org.healthylifestyle.user.model.lifestyle.healthy.Parameter;
import org.healthylifestyle.user.model.lifestyle.healthy.Status;

public interface ParameterService {
	Parameter save(ParameterSaveRequest saveRequest) throws ValidationException;

	Parameter findByUserAndParameterTypeAndStatus(Long userId, Long parameterTypeId, Status status);

	List<Parameter> findAllByUserAndStatus(Long userId, Status status);

	List<Parameter> findByParameterTypeAndStatus(Long parameterTypeId, Status status);

}
