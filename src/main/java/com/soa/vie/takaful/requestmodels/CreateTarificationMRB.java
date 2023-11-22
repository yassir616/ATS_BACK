package com.soa.vie.takaful.requestmodels;


import com.soa.vie.takaful.entitymodels.ProduitMrb;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateTarificationMRB {
	private String natureBienAssure;

	//private Float tauxContribution;
	
	//private Integer ageMax;
	
	//private Integer ageMin;
	
	private Float valeurBatiment;
	
	private Float contenu;

	private Float primeNet;
	
	private ProduitMrb produitMrb;

}
