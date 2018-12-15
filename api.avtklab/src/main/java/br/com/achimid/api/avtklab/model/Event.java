package br.com.achimid.api.avtklab.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class Event extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "O campo description não pode ser null")
    private String description;

    @NotNull(message = "O campo startDate não pode ser null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @NotNull(message = "O campo endDate não pode ser null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

}
