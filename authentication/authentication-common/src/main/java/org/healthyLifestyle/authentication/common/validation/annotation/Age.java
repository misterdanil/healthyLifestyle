package org.healthyLifestyle.authentication.common.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.healthyLifestyle.authentication.common.validation.AgeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
@Documented
public @interface Age {
	int value() default 18;
	
	String message() default "Age must be 18 or bigger";

	Class<?>[] groups() default {};
    
	Class<? extends Payload>[] payload() default {};
}
