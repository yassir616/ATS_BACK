package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class AgendaModelResponse extends AbstractBaseEntity {
    /**
     *
     */
    private Date eventStartDate;

    private Date eventEndDate;
    
    private String eventDescription;
    
    private String eventColor;

    @JsonIgnore
    private TakafulUser user;
 


    


}

