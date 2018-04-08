package me.efraimgentil.jsr303.resource;

import me.efraimgentil.jsr303.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {


    @Autowired
    UserRepository userRepository;

    @GET
    public Response getUser( @NotNull @Min(1) @Max(50) @QueryParam("limit") Integer limit){

        return Response.ok(userRepository.findAll()).build();
    }

}
