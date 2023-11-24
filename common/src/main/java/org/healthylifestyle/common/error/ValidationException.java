package org.healthylifestyle.common.error;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.web.ErrorParser;
import org.springframework.validation.BindingResult;

public class ValidationException extends Exception {
	private ErrorResult errorResult;

	public ValidationException(String message, ErrorResult errorResult) {
		super(message);
		this.errorResult = errorResult;
	}

	public ValidationException(String message, BindingResult result) {
		super(message);
		errorResult = ErrorParser.getErrorResult(result);
	}

	public ErrorResult getErrorResult() {
		return errorResult;
	}
}
