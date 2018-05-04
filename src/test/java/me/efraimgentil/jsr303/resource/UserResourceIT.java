package me.efraimgentil.jsr303.resource;

import me.efraimgentil.jsr303.model.User;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class UserResourceIT extends BaseITConfig {


    private final String GET_USERS = "/users";
    private final String CREATE_USER = GET_USERS;
    private final String GET_USER = "/users/{userId}";

    @Test
    public void shouldReturnBadRequestIfLimitIsNotInformed(){
        when()
                .get(GET_USERS)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("[0].fieldName" , is("limit"))
                .body("[0].error" , is("must not be null"));
    }

    @Test
    public void shouldReturnBadRequestIfLimitHasInvalidMinimumValue(){
        given()
                .queryParam("limit" , 0)
        .when()
                .get(GET_USERS)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("[0].fieldName" , is("limit"))
                .body("[0].error" , is("must be between 1 and 50"));
    }

    @Test
    public void shouldReturnBadRequestIfLimitAndPageHasInvalidValue(){
        given()
                .queryParam("limit" , 0)
                .queryParam("page" , -10)
        .when()
                .get(GET_USERS)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("$.size()" , is(2))
                .body("fieldName" , hasItems("limit" , "page"))
                .body("findAll{ it.fieldName == 'limit' }.error" , hasItems("must be between 1 and 50"))
                .body("findAll{ it.fieldName == 'page' }.error" , hasItems("must be between 0 and " + Integer.MAX_VALUE));
    }

    @Test
    public void shouldSuccessfullyReturnAnPageOfUsers(){
        given()
                .queryParam("limit" , 10)
        .when()
                .get(GET_USERS)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("$" , hasKey("content"))
                .body("$" , hasKey("last"))
                .body("$" , hasKey("totalPages"))
                .body("$" , hasKey("totalElements"))
                .body("size" , is(10))
                .body("number" , is(0));
    }


    @Test
    public void shouldReturnBadRequestIfUserIdDoesNotExists(){
        given()
                .pathParam("userId" , Integer.MAX_VALUE)
        .when()
                .get(GET_USER)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("[0].fieldName" , is("userId"))
                .body("[0].error" , is("User does not exists"));
    }

    @Test
    public void shouldReturnTheUserIfTheUserIdExists(){
        given()
                .pathParam("userId" , 1)
                .when()
                .get(GET_USER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$" , hasKey("id"))
                .body("$" , hasKey("fullName"))
                .body("$" , hasKey("preferedName"))
                .body("$" , hasKey("userName"));
    }

    @Test
    public void shouldCreateAnNewUser(){
        given()
                .body(new User())
        .when()
                .post(CREATE_USER)
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

}
