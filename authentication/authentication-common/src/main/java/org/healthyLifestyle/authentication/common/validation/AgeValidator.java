package org.healthyLifestyle.authentication.common.validation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;

import org.healthyLifestyle.authentication.common.validation.annotation.Age;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<org.healthyLifestyle.authentication.common.validation.annotation.Age, Date> {
	private Integer age;

	@Override
	public void initialize(Age constraintAnnotation) {
		age = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		LocalDate birthDate = LocalDate.ofInstant(value.toInstant(), ZoneOffset.UTC);
		LocalDate now = LocalDate.now();

		return Period.between(birthDate, now).getYears() >= age;

	}

}
