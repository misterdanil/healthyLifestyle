package org.healthylifestyle.common.error;

import java.util.LinkedHashMap;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

public class BindingResultFactory {
	private BindingResultFactory() {
	}

	public static <T> BindingResult getInstance(T target, String name, Validator validator) {
		BindingResult validationResult = new BeanPropertyBindingResult(target, name);
		validator.validate(target, validationResult);

		return validationResult;
	}

	public static BindingResult getInstance(String name) {
		BindingResult validationResult = new MapBindingResult(new LinkedHashMap<>(), name);

		return validationResult;
	}

}
