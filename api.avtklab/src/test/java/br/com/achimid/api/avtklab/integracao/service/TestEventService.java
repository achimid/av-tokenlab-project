package br.com.achimid.api.avtklab.integracao.service;


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

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestEventService {

    @Autowired
    private EventService eventService;

    protected EventDTO eventValid;

    @Before
    public void init() {
        eventValid = EventStub.getInstance().getValidEvent();
    }

    @Test
    public void testSave() {
        Event e = eventService.save(eventValid.getEvent());
        Assert.assertNotNull(e);
        Assert.assertNotNull(e.getId());
    }

    @Test
    public void testEdit() {
        Event e = eventService.save(eventValid.getEvent());

        e.setDescription("teste");
        eventService.save(e);

        Event find = eventService.findById(e.getId());

        Assert.assertNotNull(find);
        Assert.assertNotNull(find.getId());
        Assert.assertEquals(e.getDescription(), find.getDescription());
        Assert.assertEquals("teste", find.getDescription());
    }

    @Test
    public void testExists() {
        boolean exist = eventService.exists(1);
        Assert.assertFalse(exist);

        Event e = eventService.save(eventValid.getEvent());
        exist = eventService.exists(e.getId());
        Assert.assertTrue(exist);
    }

    @Test
    public void testFindAll() {

        Event e1 = eventService.save(eventValid.getEvent());
        Event e2 = eventService.save(eventValid.getEvent());
        Event e3 = eventService.save(eventValid.getEvent());
        Event e4 = eventService.save(eventValid.getEvent());

        List<Event> events = (List<Event>) eventService.findAll();

        Assert.assertTrue(events.contains(e1));
        Assert.assertTrue(events.contains(e2));
        Assert.assertTrue(events.contains(e3));
        Assert.assertTrue(events.contains(e4));

    }

    @Test
    public void testFindById() {
        boolean exist = eventService.exists(1);
        Assert.assertFalse(exist);

        Event e = eventService.save(eventValid.getEvent());
        Event e2 = eventService.findById(e.getId());

        Assert.assertEquals(e.getId(), e2.getId());
    }


}