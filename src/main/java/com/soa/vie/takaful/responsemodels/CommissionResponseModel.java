package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Produit;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommissionResponseModel extends AbstractBaseEntity {

	/**
	 * 
	 */
	private Date dateDebut;

	private Date dateFin;
	
	private Float tva;
	
	private String commissionPartenaire;

	@JsonIgnore
	private Produit produit;

}
