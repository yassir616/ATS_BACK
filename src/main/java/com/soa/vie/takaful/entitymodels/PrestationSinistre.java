package com.soa.vie.takaful.entitymodels;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "prestationId")
public class PrestationSinistre extends Prestation {

	private static final long serialVersionUID = 1L;

	@Column(unique = false)
	private String numeroSinistre;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm")
	private Date dateDeclarationSinistre;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm")
	private Date dateSurvenance;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm")
	private Date dateResiliation;

	@Column
	private Float montantRegler;

	@Column
	private String natureSinistre;

	@Column
	private Integer dureeAvantSurvenance;

	@Column
	private Integer dureeAvantDeclaration;

	@Column
	private Float crd;

	@Column
	private Integer nbrEcheanceImpaye;

	@Column
	private Float tauxInvalidite;

	@Column
	private String numeroCompte;

	@Column
	private String commentaire;

	@Column
	private String reglementPartiel;

	@Column
	private Boolean remplie;

	@Column
	private String motif;

	@Column
	private Boolean beneficiaireAgenceBancaire;

	@ManyToMany
	@JoinTable(name = "sinistre_exclusion", joinColumns = @JoinColumn(name = "sinistre_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "exclusion_id", referencedColumnName = "id"))
	private List<Exclusion> exclusions;

	@Column
	private String causeSinistre;

}