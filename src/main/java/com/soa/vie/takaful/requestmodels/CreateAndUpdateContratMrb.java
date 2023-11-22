package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.Personne;
import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.entitymodels.ProduitMrb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateContratMrb {

	private String numeroContrat;
	private String numeroDossierFinancement;
	private String typeBatiment;
	private String adresseBatiment;
	private String numeroTitreFoncier;
	private String superficie;
	private Periodicite periodicite;
	private String description;
	private Date dateEffet;
	private Date dateEcheance;
	private Personne souscripteur;
	private PersonnePhysique assure;
	private ProduitMrb produitMrb;
	private Float valeurContenu;
	private String valeurBatiment;
	//private String situationRisque;
	private Integer dureeContrat;
	private Float primeNette;

	private Float primeTTC;
	private Float primeHT;
	private Float primeEvcat;
	private Float FESC;
	private Float taxe;
	private Float taxeParaFiscale;
	private Float prorata;
	private Float prorataTTC;
	private Float reduction;
	private List<Exclusion> exclusionsContrat;
	private List<CreateAndUpdateDomestiqueSup> domestique;
	private String idPointVente;
	private String categorie;
	private String typePropriete;
}
