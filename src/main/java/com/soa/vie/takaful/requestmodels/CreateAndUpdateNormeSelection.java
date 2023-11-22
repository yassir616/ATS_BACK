package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.Honoraire;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateNormeSelection {

    private  Integer ageMax;

	private Integer ageMin;
	
	private Float capitalMax;
	
    private Float capitalMin;
    
    private DecesProduit decesProduitId;

    private List<Honoraire> honoraires;

}