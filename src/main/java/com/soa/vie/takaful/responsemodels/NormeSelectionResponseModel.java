package com.soa.vie.takaful.responsemodels;


import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.Honoraire;
import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NormeSelectionResponseModel extends AbstractBaseEntity {

    /**
     *
     */
    private Float capitalMin;

    private Float capitalMax;

    private Integer ageMin;

    private Integer ageMax;

    @JsonIgnore
    private DecesProduit decesProduit;
 
    private List<Honoraire> honoraires;


}