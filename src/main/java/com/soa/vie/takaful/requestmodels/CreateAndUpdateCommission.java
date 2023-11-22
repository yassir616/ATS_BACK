package com.soa.vie.takaful.requestmodels;

import java.util.Date;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateAndUpdateCommission {
	
	private Date dateDebut;

	private Date dateFin;

	private Float tva;

	private String commissionPartenaire;

	private String produitId;
	
	
	
}
