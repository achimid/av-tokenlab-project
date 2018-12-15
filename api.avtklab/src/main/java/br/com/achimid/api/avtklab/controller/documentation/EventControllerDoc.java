package br.com.achimid.api.avtklab.controller.documentation;

import br.com.achimid.api.avtklab.dto.EventDTO;
import br.com.achimid.api.avtklab.model.Event;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EventControllerDoc {

    @ApiOperation("Listagem de todos os eventos")
    HttpEntity<Iterable<Event>> all();

    @ApiOperation("Busca evento por id")
    HttpEntity<?> get(@PathVariable Integer id);

    @ApiOperation("Cria um evento")
    HttpEntity<Event> create(@RequestBody EventDTO event);

    @ApiOperation("Atualiza o evento")
    HttpEntity<?> update(@RequestBody EventDTO event);

    @ApiOperation("Exlusão de um evento por id")
    HttpEntity<?> delete(@PathVariable Integer id);

}
