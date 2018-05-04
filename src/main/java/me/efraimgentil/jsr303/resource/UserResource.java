package me.efraimgentil.jsr303.resource;

import me.efraimgentil.jsr303.model.User;
import me.efraimgentil.jsr303.repository.UserRepository;
import me.efraimgentil.jsr303.validation.annotation.UserExists;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {


    @Autowired
    UserRepository userRepository;

    @GET
    public Response list(@NotNull @Range(min = 1 , max = 50) @QueryParam("limit") Integer limit,
                            @DefaultValue("0") @QueryParam("page") @Range(min = 0 , max = Integer.MAX_VALUE) Integer page){
        return Response.ok(userRepository.findAll(PageRequest.of(0 , limit ))).build();
    }

    @GET
    @Path("{userId}")
    public Response getUser(@NotNull @UserExists @PathParam("userId") Integer userId){
        return Response.ok(userRepository.findById(userId)).build();
    }

    @POST
    public Response createUser(@Valid User user , @Context UriInfo uriInfo){
        User save = userRepository.save(user);
        URI userUri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(save.getId())).build();
        return Response.created(userUri).build();
    }

}
