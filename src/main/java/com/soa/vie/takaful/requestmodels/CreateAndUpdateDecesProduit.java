package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.PointVente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateDecesProduit {

	private String code;

	private String libelle;

	private String numeroHomologation;

	private Date dateHomologation;

	private Float fraisGestion;

	private Float tvaFraisGestion;

	private Float fraisAcquisition;

	private Float tvaFraisAcquisition;

	private String descriptif;

	private String pieceJointe;

	private Float plafondFrais;

	private Float seuilPrestation;

	private String produitType;

	private Float taxe;

	private Float montantAccessoire;

	private String categorieId;

	private String risqueId;

	private String sousCategorieId;

	private String taxeParaFiscale;

	private String responsableProduction;

	private String responsablePrestation;

	private String partenaireId;

	private Integer delaiResiliation;

	private Integer delaiPreavis;

	private Float seuilExaminateur;

	private Float seuilConseil;

	private Float seuilReassurance;

	private Integer dureeMax;

	private Integer dureeMin;

	private Integer differeMin;

	private Integer differeMax;

	private Integer ageMin;

	private Integer ageMax;

	private String codeExoneration;

	private String typeTarif;

	private Integer ageEcheance;

	private String tauxRabais;

	private String numeroCompte;

	private String codeCompte;

	private String libelleCompte;

	private Integer ageMaxEligibilite;

	private String echeanceImpayees;

	private List<Periodicite> periodicites;

	private List<Exclusion> exclusions;

	private Integer delaiEnAttente;

	private Integer ageVisite;

	private Integer delaiSansSouscription;

	private String codificationProduit;

	private List<PointVente> pointVentes;
	private List<CreateAndUpdateCommission> commissions;
	private List<CreateAndUpdateTarrification> tarrifications;
	private List<CreateAndUpdateOptionAssurance> options;
	private List<CreateAndUpdateDecesProduitCauseRestitution> decesCauseRestitution;
	private List<CreateAndUpdateTypePrestationProduit> prestations;
	private List<CreateAndUpdateNormeSelection> normes;

}
