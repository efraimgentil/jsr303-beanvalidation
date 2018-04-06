package me.efraimgentil.jsr303.resource.exceptionmapper;

import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ValidationExceptionMapperTest {

    ValidationExceptionMapper mapper;

    @Before
    public void setUp(){
        mapper = spy(new ValidationExceptionMapper());
    }

    @Test
    public void shouldReturnTheListOfTheConstraintsWithTheFieldNameAndMessage(){
        ConstraintViolationException cve = mock(ConstraintViolationException.class);
        ConstraintViolation cv1 = mock(ConstraintViolation.class);
        doReturn(new HashSet<>(Arrays.asList(cv1))).when(cve).getConstraintViolations();
        when(cv1.getMessage()).thenReturn("fake msg 1");
        doReturn("field1").when(mapper).retrieveFieldName(cv1);

        List<ValidationExceptionMapper.ConstraintError> constraintErrors = mapper.prepareErrors(cve);

        assertThat(constraintErrors.get(0).fieldName).isEqualTo("field1");
        assertThat(constraintErrors.get(0).error).isEqualTo("fake msg 1");
    }

    @Test
    public void shouldReturnLeafNodeNameIfElementKindIsPARAMTER(){
        ConstraintViolation cv1 = mock(ConstraintViolation.class);
        PathImpl rootPath = PathImpl.createRootPath();
        NodeImpl param =    rootPath.addParameterNode("expected", 0);
        when(cv1.getPropertyPath()).thenReturn(rootPath);

        String fieldName = mapper.retrieveFieldName(cv1);

        assertThat(fieldName).isEqualTo("expected");
    }

    @Test
    public void shouldReturnTheNameOfThePropertyIfTheElementKindIsProperty(){
        ConstraintViolation cv1 = mock(ConstraintViolation.class);
        PathImpl rootPath = PathImpl.createRootPath();
        NodeImpl param =    rootPath.addPropertyNode("propertyName");
        when(cv1.getPropertyPath()).thenReturn(rootPath);

        String fieldName = mapper.retrieveFieldName(cv1);

        assertThat(fieldName).isEqualTo("propertyName");
    }

}
