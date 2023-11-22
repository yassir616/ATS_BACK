package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Prestation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter
@Setter
public class PrestationSinistreModelResponse extends Prestation {

	private String numeroSinistre;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateDeclarationSinistre;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateSurvenance;

	private Date dateResiliation;

	private Float montantRegler;

	private String natureSinistre;

	private Integer dureeAvantSurvenance;

	private Integer dureeAvantDeclaration;

	private Float crd;

	private Integer nbrEcheanceImpaye;

	private Float tauxInvalidite;

	private String numeroCompte;

	private String commentaire;

	private String reglementPartiel;

	private Boolean remplie;

	private String motif;

	private Boolean beneficiaireAgenceBancaire;

	private List<Exclusion> exclusions;
	private float montantIr;
	private String causeSinistre;
	private Date dateCreation;
}