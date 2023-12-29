package org.healthylifestyle.common.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.healthylifestyle.common.dto.ErrorResult;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;

public class ErrorParser {
	private static final Logger logger = LoggerFactory.getLogger(ErrorParser.class);

	private ErrorParser() {
	}

	public static ErrorResult getErrorResult(BindingResult result) {
		if (result == null) {
			return null;
		}
		Map<String, String> fieldErrors = new LinkedHashMap<>();
		result.getFieldErrors().forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));

		List<String> globalErrors = new ArrayList<>();
		result.getGlobalErrors().forEach(e -> globalErrors.add(e.getDefaultMessage()));

		ErrorResult er = new ErrorResult(fieldErrors, globalErrors);
		return er;
	}

	public static void rejectValue(String value, String code, BindingResult validationResult,
			MessageSource messageSource, Object... args) {
		validationResult.rejectValue(value, code,
				messageSource.getMessage(code, args, LocaleContextHolder.getLocale()));
	}

	public static void reject(String code, BindingResult validationResult, MessageSource messageSource,
			Object... args) {
		validationResult.reject(code, messageSource.getMessage(code, args, LocaleContextHolder.getLocale()));
	}

	public static void checkErrors(BindingResult validationResult, String message, Type type, Object... args)
			throws ValidationException {
		if (validationResult.hasErrors()) {
			logger.debug(message);
			throw new ValidationException(message, validationResult, type, args);
		}
	}
}
