package me.efraimgentil.jsr303.validation.annotation;

import me.efraimgentil.jsr303.validation.Order1Validator;
import org.springframework.core.annotation.Order;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Order1
@Documented
@Constraint(validatedBy = Composed.ComposedValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Composed {

  String message() default "Must have 2";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

  class ComposedValidator implements ConstraintValidator<Composed,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
      return s.contains("2");
    }

  }

}
