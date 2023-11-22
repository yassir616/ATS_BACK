package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateAndUpdateEncaissement {

	private String fluxId;

	private float montantEncaissement;

	private String numReference;

	private String modeEncaissement;

	private Date dateEncaissement;

	private Date dateEtablissement;

	private float montantTaxe;

	private float montantQuittance;
	private float montantTaxeParafiscale;
	private float accessoire;
	private float montantEmission;

	private String cotisation;

	private String compteBancaire;

}
