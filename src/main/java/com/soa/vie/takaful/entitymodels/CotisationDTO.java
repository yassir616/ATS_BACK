package com.soa.vie.takaful.entitymodels;


import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CotisationDTO {

	@Column
	private String solde;// montant contribution a prelever
	private String codeProduit;
	private String codePartenaire;
	private String numeroDossierFinancement;
	private String numeroContrat;
	private String nom;
	private String prenom;
	private String cin;
	private String dateEffet;
	private String dateEcheance;
	private String datePrelevement;
	private String periodicite;
	private String numeroQuittance;
	private String rib;
	private String numeroOrdrePrelevement;
	private String nombreAppel;
	private String dureeContrat;
	private String commissionYsr;
	private String montantTTT;

}
