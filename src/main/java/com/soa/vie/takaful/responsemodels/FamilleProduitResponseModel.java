package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilleProduitResponseModel extends AbstractBaseEntity{
    
    private String name;
    private String id;
}
