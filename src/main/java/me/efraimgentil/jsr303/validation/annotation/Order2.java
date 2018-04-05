package me.efraimgentil.jsr303.validation.annotation;

import me.efraimgentil.jsr303.validation.Order1Validator;
import me.efraimgentil.jsr303.validation.Order2Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = Order2Validator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface Order2 {

    String message() default "Must have 2";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
