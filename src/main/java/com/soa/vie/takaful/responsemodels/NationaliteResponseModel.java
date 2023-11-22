package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NationaliteResponseModel extends AbstractBaseEntity {
	
    private String libelle;
    
}