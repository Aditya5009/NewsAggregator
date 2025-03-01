package com.newsaggregator.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NewsNameValidator implements ConstraintValidator<NewsNameConstraint, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return !value.matches(".*\\d.*") && !value.contains(" ");
	}

	@Override
	public void initialize(NewsNameConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
