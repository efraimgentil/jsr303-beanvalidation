package me.efraimgentil.jsr303.resource;


import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleResourceIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp(){
        RestAssured.port = serverPort;
        RestAssured.filters(Arrays.asList( new ResponseLoggingFilter()));
    }

    @Test
    public void shouldReturnErrorWhenDoesNotSendParam() {
        when()
                .get("/sample")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body( "$.size()" , is(1) )
                .body("[0]" , hasKey("fieldName"))
                .body("[0].error" , equalTo("must not be empty"));
    }

    @Test
    public void shouldReturnAllErrorsIfParameterisEmpty() {
        given()
                .queryParam("param" , "")
        .when()
                .get("/sample")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body( "$.size()" , is(2) )
                .body("[0]" , hasKey("fieldName"))
                .body("[1]" , hasKey("fieldName"))
                .body("error" , hasItems("must not be empty" , "size must be between 1 and 10"));
    }

    @Test
    public void shouldReturnParamValue() {
        given()
                .queryParam("param" , "myQuery")
        .when()
                .get("/sample")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body( "result" , is("myQuery") );
    }

    @Test
    public void shouldReturnErrorsFromTheCustomConstraints() {
        given()
                .queryParam("param" , "myQuery")
                .when()
                .get("/sample/order")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("[0]" , hasKey("fieldName"))
                .body("[1]" , hasKey("fieldName"))
                .body("error" , hasItems("Must have 2" , "Must have 1"));
    }

    @Test
    public void shouldReturnTheParamValueIfThereIsNoViolationOfTheCustomConstraints() {
        given()
                .queryParam("param" , "myQuery12")
                .when()
                .get("/sample/order")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body( "result" , is("myQuery12") );
    }

    @Test
    public void shouldReturnTheValidationsOfTheComposedConstraints() {
        given()
                .queryParam("param1" , "myParam")
                .queryParam("param2" , "myParam")
        .when()
                .get("/sample/composed")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body( "$.size()" , is(3) )
                .body("fieldName" , hasItems("param1" , "param2"))
                .body("findAll{ it.fieldName = 'param2'}.error", hasItems("Must have 2"))
                .body("findAll{ it.fieldName = 'param1'}.error", hasItems("Must have 2" , "Must have 1"));
    }

    @Test
    public void shouldReturnTheValidationsOfTheGroup1() {
        given()
                .queryParam("param1" , "myParam")
                .queryParam("param2" , "myParam")
        .when()
                .get("/sample/group")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body( "$.size()" , is(2) )
                .body("fieldName" , hasItems("param1" , "param2"))
                .body("findAll{ it.fieldName = 'param2'}.error", hasItems("Must have 1"))
                .body("findAll{ it.fieldName = 'param1'}.error", hasItems("Must have 1"));
    }

    @Test
    public void shouldReturnTheValidationsOfTheGroup2() {
        given()
                .queryParam("param1" , "myParam1")
                .queryParam("param2" , "myParam1")
        .when()
                .get("/sample/group")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body( "$.size()" , is(2) )
                .body("fieldName" , hasItems("param1" , "param2"))
                .body("findAll{ it.fieldName = 'param2'}.error", hasItems("Must have 2"))
                .body("findAll{ it.fieldName = 'param1'}.error", hasItems("Must have 2"));
    }

}
