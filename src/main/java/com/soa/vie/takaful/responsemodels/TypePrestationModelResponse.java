package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.FamilleProduit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypePrestationModelResponse extends AbstractBaseEntity {

    /**
     *
     */
    private String code;

    private String libelle;

    private String name;
    
    private FamilleProduit famille;

}