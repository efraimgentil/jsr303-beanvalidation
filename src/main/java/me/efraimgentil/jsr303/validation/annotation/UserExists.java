package me.efraimgentil.jsr303.validation.annotation;

import me.efraimgentil.jsr303.validation.UserExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistsValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExists {

    String message() default "User does not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
