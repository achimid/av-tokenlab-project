package br.com.achimid.api.avtklab.service;

import br.com.achimid.api.avtklab.model.Event;
import br.com.achimid.api.avtklab.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event save(Event event){
        return eventRepository.save(event);
    }

    public void deleteById(Integer id){
        eventRepository.deleteById(id);
    }

    public Iterable<Event> findAll(){
        return eventRepository.findAll();
    }

    public Event findById(Integer id){
        return eventRepository.findById(id).get();
    }

    public boolean exists(Integer id){
        return eventRepository.existsById(id);
    }

}
