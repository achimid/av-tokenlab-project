package br.com.achimid.api.avtklab.dto;

import br.com.achimid.api.avtklab.model.BaseModel;
import br.com.achimid.api.avtklab.model.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EventDTO{

    private Integer id;
    @NotNull(message = "O campo description não pode ser null")
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    @NotNull(message = "O campo startDate não pode ser null")
    private Date startDate;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    @NotNull(message = "O campo endDate não pode ser null")
    private Date endDate;

    public EventDTO(){

    }

    public EventDTO(Event event){
        if(event == null) return;

        this.id = event.getId();
        this.description = event.getDescription();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }

    @JsonIgnore
    public Event getEvent(){
        Event event = new Event();
        event.setId(this.id);
        event.setDescription(this.description);
        event.setEndDate(this.endDate);
        event.setStartDate(this.startDate);

        return event;
    }

}
