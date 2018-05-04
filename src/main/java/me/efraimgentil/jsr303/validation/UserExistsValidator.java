package me.efraimgentil.jsr303.validation;

import me.efraimgentil.jsr303.repository.UserRepository;
import me.efraimgentil.jsr303.validation.annotation.UserExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserExistsValidator implements ConstraintValidator<UserExists,Integer> {

    private UserRepository userRepository;

    @Autowired
    public UserExistsValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value != null) {
           return userRepository.existsById(value);
        }
        return true;
    }

}
