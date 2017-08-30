package com.wsc.qa.constraint;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNull.Validator.class)
public @interface NotNull {

	String message() default "不允许为Null";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public class Validator implements Verifiable<NotNull, Object> {

		@Override
		public void initialize(NotNull constraintAnnotation) {
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			return validate(value);
		}

		@Override
		public boolean canVerify(Class<? extends Annotation> clazz) {
			return NotNull.class.equals(clazz);
		}

		@Override
		public boolean verify(NotNull annotation, Object value) {
			return validate(value);
		}

		private boolean validate(Object value) {
			return value != null;
		}

	}

}
