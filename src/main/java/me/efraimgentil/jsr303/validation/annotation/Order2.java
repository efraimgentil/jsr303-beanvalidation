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
public @interface Order2 {

    public abstract String message() default "Must have 2";
    public abstract Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};

}
