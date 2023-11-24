package org.healthylifestyle.common.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.healthylifestyle.common.dto.ErrorResult;
import org.springframework.validation.BindingResult;

public class ErrorParser {

	private ErrorParser() {
	}

	public static ErrorResult getErrorResult(BindingResult result) {
		Map<String, String> fieldErrors = new LinkedHashMap<>();
		result.getFieldErrors().forEach(e -> fieldErrors.put(e.getField(), e.getDefaultMessage()));

		List<String> globalErrors = new ArrayList<>();
		result.getGlobalErrors().forEach(e -> globalErrors.add(e.getDefaultMessage()));

		ErrorResult er = new ErrorResult(fieldErrors, globalErrors);
		return er;
	}
}
