package me.efraimgentil.jsr303.validation;

import me.efraimgentil.jsr303.model.User;
import me.efraimgentil.jsr303.repository.UserRepository;
import me.efraimgentil.jsr303.validation.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {

    private UserRepository userRepository;

    @Autowired
    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        if(!isEmpty(value)){
            boolean b = userRepository.existsByUserName(value.getUserName());
            if(b){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(UniqueUsername.MESSAGE)
                        .addPropertyNode("userName")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
