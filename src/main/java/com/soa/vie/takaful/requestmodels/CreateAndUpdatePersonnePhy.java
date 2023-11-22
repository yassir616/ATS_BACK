package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.util.SexeEnum;
import com.soa.vie.takaful.util.SituationFamilialeEnum;
import com.soa.vie.takaful.util.VoisEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdatePersonnePhy {

	private boolean isProspect;

	private String logo;

	private String nom;

	private String prenom;

	private String numTiers;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateNaissance;

	private SexeEnum sexe;

	private String cin;

	private String rib;

	private String adressPays;

	private String adressVille;

	private VoisEnum adressVois;

	private String adressComplement;

	private String adressCodePostal;

	private String adressNumero;

	private String adressComplete;

	private String vivant;

	private String matricule;

	private String controleTerrorisme;

	private String terrorisme;

	private Integer nombreEnfants;

	private SituationFamilialeEnum situationFamiliale;

	private String profession;

	private String nationalite;

	private String numeroDeTelephone;

	private String identite;
}