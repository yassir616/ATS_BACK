package com.soa.vie.takaful.entitymodels;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "BeneficiaireId")
public class BeneficiaireAgence extends BeneficiaireSinistre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  @ManyToOne
	    private PrestationSinistre sinistre;
	  
	  @ManyToOne
	    private PointVente pointVente;

}
