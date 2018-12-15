package br.com.achimid.api.avtklab.dto;

import br.com.achimid.api.avtklab.model.BaseModel;
import br.com.achimid.api.avtklab.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class EventDTO{

    private Integer id;
    private String description;
    private Date startDate;
    private Date endDate;

    public EventDTO(Event event){
        if(event == null) return;

        this.id = event.getId();
        this.description = event.getDescription();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
    }

}
