package me.efraimgentil.jsr303.validation.annotation;

import me.efraimgentil.jsr303.validation.Order1Validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Order1
@Documented
@Constraint(validatedBy = ComposedSingle.ComposedSingleValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface ComposedSingle {

  String message() default "Must have 2";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

  class ComposedSingleValidator implements ConstraintValidator<Composed,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
      return s.contains("2");
    }

  }

}
