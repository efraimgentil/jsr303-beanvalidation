package me.efraimgentil.jsr303.resource;

import me.efraimgentil.jsr303.validation.annotation.Composed;
import me.efraimgentil.jsr303.validation.annotation.ComposedSingle;
import me.efraimgentil.jsr303.validation.annotation.Order1;
import me.efraimgentil.jsr303.validation.annotation.Order2;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/sample")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {



    @GET
    @Path("/")
    public Response get(@NotEmpty @Max(10) @QueryParam("param") String param){
        return  Response.ok(param).build();
    }


    @GET
    @Path("/pattern")
    public Response pattern(@Pattern(regexp = ".*2.*") @Pattern(regexp = ".*1.*")  @Pattern(regexp = ".*3.*")   @QueryParam("param") String param){
        return  Response.ok(param).build();
    }

    @GET
    @Path("/order")
    public Response order(@Pattern(regexp = ".*4.*") @Order1 @Order2 @QueryParam("param") String param){
        return  Response.ok(param).build();
    }

    public static class ComposedParam{
        @Composed
        @QueryParam("param1")
        public String param1;
        @ComposedSingle
        @QueryParam("param2")
        public String param2;
    }

    @GET
    @Path("/composed")
    public Response composedParam(@BeanParam @Valid ComposedParam param){
        return  Response.ok(param).build();
    }

    public static class GroupValidation{
        @Composed
        @QueryParam("param1")
        public String param1;
        @ComposedSingle
        @QueryParam("param2")
        public String param2;
    }

    @GET
    @Path("/composed")
    public Response composedParam(@BeanParam @Valid ComposedParam param){
        return  Response.ok(param).build();
    }

}
