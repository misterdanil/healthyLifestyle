package org.healthylifestyle.common.error;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.web.ErrorParser;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;

public class ValidationException extends Exception {
	private ErrorResult errorResult;
	private Type type;

	public ValidationException(String message, ErrorResult errorResult) {
		super(message);
		this.errorResult = errorResult;
		type = Type.BAD_REQUEST;
	}

	public ValidationException(String message, BindingResult result) {
		super(message);
		errorResult = ErrorParser.getErrorResult(result);
		type = Type.BAD_REQUEST;
	}

	public ValidationException(String message, BindingResult result, Object... parameters) {
		super(String.format(message, parameters));
		errorResult = ErrorParser.getErrorResult(result);
		type = Type.BAD_REQUEST;
	}

	public ValidationException(String message, BindingResult result, Type type, Object... parameters) {
		super(String.format(message, parameters));
		errorResult = ErrorParser.getErrorResult(result);
		this.type = type;
	}

	public ErrorResult getErrorResult() {
		return errorResult;
	}

	public Type getType() {
		return type;
	}
}
