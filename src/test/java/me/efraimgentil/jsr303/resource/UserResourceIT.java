package me.efraimgentil.jsr303.resource;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.when;

public class UserResourceIT extends BaseITConfig {


    @Test
    public void shouldReturnBadRequestIfNotUserIdIsInformed(){
        when()
                .get("/users")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
