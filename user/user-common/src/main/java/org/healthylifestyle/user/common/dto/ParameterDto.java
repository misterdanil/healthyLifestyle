package org.healthylifestyle.user.common.dto;

public class ParameterDto {
	private Long id;
	private ParameterTypeDto parameterType;
	private String value;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ParameterTypeDto getParameterType() {
		return parameterType;
	}

	public void setParameterType(ParameterTypeDto parameterType) {
		this.parameterType = parameterType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
