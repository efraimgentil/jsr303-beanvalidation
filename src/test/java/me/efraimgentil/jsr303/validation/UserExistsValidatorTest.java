package me.efraimgentil.jsr303.validation;

import me.efraimgentil.jsr303.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserExistsValidatorTest {


    @Mock
    UserRepository userRepository;
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    UserExistsValidator validator;

    @Before
    public void setUp(){
        validator = new UserExistsValidator(userRepository);
    }

    @Test
    public void shouldReturnTrueIfValueIsNull(){
        assertThat(validator.isValid(null ,constraintValidatorContext )).isTrue();
    }


    @Test
    public void shouldReturnTrueIfValueExistInTheDatabase(){
        Integer value = 1;
        when(userRepository.existsById(value)).thenReturn(true);

        assertThat(validator.isValid(value ,constraintValidatorContext )).isTrue();
    }

    @Test
    public void shouldReturnFalseIfValueDoesNotExistInTheDatabase(){
        Integer value = 1;
        when(userRepository.existsById(value)).thenReturn(false);

        assertThat(validator.isValid(value ,constraintValidatorContext )).isFalse();
    }


}
