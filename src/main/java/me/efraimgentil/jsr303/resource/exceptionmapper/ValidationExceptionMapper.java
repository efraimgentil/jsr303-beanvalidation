package me.efraimgentil.jsr303.resource.exceptionmapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        System.out.println(e);
        e.printStackTrace();
        List<String> collect = e.getConstraintViolations().stream().map(cv -> cv.getPropertyPath() + " " + cv.getMessage()).collect(Collectors.toList());
        //oh yeah... you need to shell out some $$ !
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(collect)
                .build();
    }
}
