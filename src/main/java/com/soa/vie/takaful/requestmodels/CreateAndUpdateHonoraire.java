package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateHonoraire {
  
	private  String code;

	private Float montantHonoraire;
	
	private String libelle;
	
	private String typeAuxiliaireId;


}
