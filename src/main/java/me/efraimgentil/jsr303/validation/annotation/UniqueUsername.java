package me.efraimgentil.jsr303.validation.annotation;

import me.efraimgentil.jsr303.validation.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
