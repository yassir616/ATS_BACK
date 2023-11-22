package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.entitymodels.DecesProduit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateOptionAssurance {
	
    private String libelle;
    
    private Float min;

    private Float max;
    
    private DecesProduit decesProduitId;
      
}