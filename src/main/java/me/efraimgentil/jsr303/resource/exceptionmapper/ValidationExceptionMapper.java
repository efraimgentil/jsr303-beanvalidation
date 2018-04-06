package me.efraimgentil.jsr303.resource.exceptionmapper;

import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    public static class ConstraintError{
        public String fieldName;
        public String error;

        public ConstraintError(String fieldName, String error) {
            this.fieldName = fieldName;
            this.error = error;
        }
    }

    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareErrors(e))
                .build();
    }

    protected List<ConstraintError> prepareErrors(ConstraintViolationException cve){
        return cve.getConstraintViolations().stream()
                .map( cv -> new ConstraintError(retrieveFieldName( cv ), cv.getMessage()) )
                .collect(Collectors.toList());
    }

    protected String retrieveFieldName(ConstraintViolation cv){
        PathImpl path = ((PathImpl)cv.getPropertyPath());
        if(ElementKind.PARAMETER.equals(path.getLeafNode().getKind())){
            return path.getLeafNode().getName();
        }

        Iterable<Path.Node> iterable = () -> path.<Path.Node>iterator();
        return StreamSupport.stream(iterable.spliterator(), false)
                .filter(node -> node.getKind() != ElementKind.METHOD && node.getKind() != ElementKind.PARAMETER)
                .map(n -> n.toString())
                .findFirst().orElse(path.getLeafNode().getName());
    }

}
