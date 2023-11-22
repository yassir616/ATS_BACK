package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.entitymodels.DecesProduit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateTarrification {

	private Integer ageMax;

	private Integer ageMin;

	private Float capitalMax;

	private Float capitalMin;

	private Integer dureeMax;

	private Integer dureeMin;

	private Float differeMax;

	private Float differeMin;

	// taux = taux annulelle
	private Float taux;

	private Float tauxMensuelle;

	private Float forfait;

	private DecesProduit decesProduitId;
}