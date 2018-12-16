package br.com.achimid.api.avtklab.controller;


import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestEventController {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("admin");
        RestAssured.authentication = authScheme;
    }

    @Test
    public void testEventEndPoint_badAuth() {
        given().auth()
                .preemptive().basic("", "")
                .when().get("/api/v1/event")
                .then()
                .assertThat().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void testEventEndPointWithoutReturn() {
        //given().auth().preemptive().basic(username, password).when().
        get("/api/v1/event")
            .then()
            .assertThat().statusCode(HttpStatus.OK.value())
            .assertThat().body(containsString("[]"));
    }

}