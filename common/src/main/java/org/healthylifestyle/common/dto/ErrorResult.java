package org.healthylifestyle.common.dto;

import java.util.List;
import java.util.Map;

public class ErrorResult {
	private Map<String, String> fieldErrors;
	private List<String> globalErrors;

	public ErrorResult(Map<String, String> fieldErrors, List<String> globalErrors) {
		this.fieldErrors = fieldErrors;
		this.globalErrors = globalErrors;
	}

	public ErrorResult(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public ErrorResult(List<String> globalErrors) {
		this.globalErrors = globalErrors;
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public List<String> getGlobalErrors() {
		return globalErrors;
	}

	public void setGlobalErrors(List<String> globalErrors) {
		this.globalErrors = globalErrors;
	}

}
