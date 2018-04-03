package me.efraimgentil.jsr303.validation;

import me.efraimgentil.jsr303.validation.annotation.Order1;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Order1Validator implements ConstraintValidator<Order1,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.contains("1");
    }

}
