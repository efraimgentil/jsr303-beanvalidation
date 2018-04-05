package me.efraimgentil.jsr303.resource;

import me.efraimgentil.jsr303.validation.annotation.Composed;
import me.efraimgentil.jsr303.validation.annotation.ComposedSingle;
import me.efraimgentil.jsr303.validation.annotation.Order1;
import me.efraimgentil.jsr303.validation.annotation.Order2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/sample")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {

    public static class Result{
        private Object result;

        public Result(Object result) {
            this.result = result;
        }

        public Object getResult() {
            return result;
        }
    }


    @GET
    @Path("/")
    public Response get(@NotEmpty @Size(min = 1 , max = 10) @QueryParam("param") String param){
        return  Response.ok(new Result(param)).build();
    }

    @GET
    @Path("/order")
    public Response order(@Order1 @Order2 @QueryParam("param") String param){
        return  Response.ok(new Result(param)).build();
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
        return  Response.ok(new Result(param)).build();
    }

    public interface Group1{}
    public interface Group2{}

    @GroupSequence({ GroupValidation.class , Group1.class , Group2.class })
    public static class GroupValidation{

        @Order1(groups = Group1.class)
        @Order2(groups = Group2.class)
        @QueryParam("param1")
        public String param1;

        @Order1(groups = Group1.class)
        @Order2(groups = Group2.class)
        @QueryParam("param2")
        public String param2;
    }

    @GET
    @Path("/group")
    public Response groupValidation(@BeanParam @Valid GroupValidation param){
        return  Response.ok(new Result(param)).build();
    }

}
