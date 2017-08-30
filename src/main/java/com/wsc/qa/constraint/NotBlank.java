package com.wsc.qa.constraint;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlank.Validator.class)
public @interface NotBlank {

	String message() default "不允许为Blank";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public class Validator implements Verifiable<NotBlank, CharSequence> {

		@Override
		public void initialize(NotBlank constraintAnnotation) {
		}

		@Override
		public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
			return validate(value);
		}

		@Override
		public boolean canVerify(Class<? extends Annotation> clazz) {
			return NotBlank.class.equals(clazz);
		}

		@Override
		public boolean verify(NotBlank annotation, CharSequence value) {
			return validate(value);
		}

		private boolean validate(CharSequence charSequence) {
			return StringUtils.isNotBlank(charSequence);
		}

	}

}
