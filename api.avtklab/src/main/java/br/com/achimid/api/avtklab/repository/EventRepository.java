package br.com.achimid.api.avtklab.repository;

import br.com.achimid.api.avtklab.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
}
