package me.efraimgentil.jsr303.validation;

import me.efraimgentil.jsr303.validation.annotation.Order1;
import me.efraimgentil.jsr303.validation.annotation.Order2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Order2Validator implements ConstraintValidator<Order2,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.contains("2");
    }

}
