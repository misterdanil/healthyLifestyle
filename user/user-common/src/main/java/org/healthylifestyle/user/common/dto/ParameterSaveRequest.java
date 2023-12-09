package org.healthylifestyle.user.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ParameterSaveRequest {
	@NotNull
	private Long parameterTypeId;
	@NotBlank
	private String value;

	public Long getParameterTypeId() {
		return parameterTypeId;
	}

	public void setParameterTypeId(Long parameterTypeId) {
		this.parameterTypeId = parameterTypeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
