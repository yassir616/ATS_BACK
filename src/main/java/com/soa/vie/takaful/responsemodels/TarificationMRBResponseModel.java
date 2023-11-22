package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.ProduitMrb;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class TarificationMRBResponseModel extends AbstractBaseEntity {

	private String natureBienAssure;

	//private Float tauxContribution;

	//private Integer ageMax;
	
	//private Integer ageMin;
	
	//private Float valeurMax;
	
	//private Float valeurMin;
	private Float valeurBatiment;

	private Float contenu;

	private Float primeNet;
	
	@JsonIgnore
	private ProduitMrb produitMrb;
}
