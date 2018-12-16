package br.com.achimid.api.avtklab.controller;


import br.com.achimid.api.avtklab.dto.EventDTO;
import br.com.achimid.api.avtklab.model.Event;
import br.com.achimid.api.avtklab.service.EventService;
import br.com.achimid.api.avtklab.stub.EventStub;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestEventController {

    @Autowired
    private EventService eventService;

    @LocalServerPort
    private int port;

    protected EventDTO eventValid;

    @Before
    public void init() {
        eventValid = EventStub.getInstance().getValidEvent();
    }

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
    public void testEventEndPoint_ListAll_WithoutReturn() {
        get("/api/v1/event")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .assertThat().body(containsString("[]"));
    }


    @Test
    public void testEventEndPoint_ListAll_WithReturn() {
        eventService.save(eventValid.getEvent());
        List<Event> events = (List<Event>) eventService.findAll();
        List<Event> eventsReturn = Arrays.asList(get("/api/v1/event").as(Event[].class));

        Assert.assertEquals(events.size(), eventsReturn.size()); // nada bom....
    }

    @Test
    public void testEventEndPoint_Create_EmptyEvent() {
        given()
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(new EventDTO())
                .post("/api/v1/event")
                .then()
                .assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testEventEndPoint_Create_InvalidDescriptionEvent() {
        eventValid.setDescription(null);

        given()
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(eventValid)
                .post("/api/v1/event")
                .then()
                .assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testEventEndPoint_Create_InvalidStartDateEvent() {
        eventValid.setStartDate(null);

        given()
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(eventValid)
                .post("/api/v1/event")
                .then()
                .assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testEventEndPoint_Create_InvalidEndDateEvent() {
        eventValid.setEndDate(null);

        given()
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(eventValid)
                .post("/api/v1/event")
                .then()
                .assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testEventEndPoint_Create_Success() {
        given()
                .header("Accept", "application/json")
                .contentType("application/json")
                .body(eventValid)
                .post("/api/v1/event")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value());
    }


}