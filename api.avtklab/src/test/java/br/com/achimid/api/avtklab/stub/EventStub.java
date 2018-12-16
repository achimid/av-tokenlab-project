package br.com.achimid.api.avtklab.stub;

import br.com.achimid.api.avtklab.dto.EventDTO;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EventStub {
    private static EventStub ourInstance = new EventStub();

    public static EventStub getInstance() {
        return ourInstance;
    }

    private EventStub() {
    }

    public EventDTO getValidEvent(){
        Calendar c = GregorianCalendar.getInstance();

        EventDTO event = new EventDTO();
        event.setDescription("Test Description");
        event.setStartDate(c.getTime());

        c.add(Calendar.HOUR_OF_DAY, 2);

        event.setEndDate(c.getTime());

        return event;
    }
}
