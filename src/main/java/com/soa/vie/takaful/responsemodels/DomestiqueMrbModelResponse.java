package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.ContratMrb;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomestiqueMrbModelResponse extends AbstractBaseEntity {

    private String nom;
    
    private String prenom;
    
    private Float masseSalarialeAnnuelle;
    
    private Float taux;
    
	@JsonIgnore
  private ContratMrb domestiqueMrb;
}