package me.efraimgentil.jsr303.validation.annotation;

import me.efraimgentil.jsr303.validation.Order1Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = Order1Validator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface Order1 {

    String message() default "Must have 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
