package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DecesProduitResponseModel extends Produit {

    /**
     * 
     */
    private String id;
    private Integer delaiResiliation;

    private Integer delaiPreavis;

    private Float seuilExaminateur;

    private Float seuilConseil;

    private Integer calculCRD;

    private Float seuilReassurance;

    private Integer dureeMax;

    private Integer dureeMin;

    private Integer differeMin;

    private Integer differeMax;

    private Integer ageMin;

    private Integer ageMax;

    private Integer ageVisite;

    private String codificationProduit;

    private String codeExoneration;

    private String typeTarif;

    private Integer ageEcheance;

    private String tauxRabais;

    private Integer ageMaxEligibilite;

    private String echeanceImpayees;

    private String numeroPolice;

    private String taxeParaFiscale;

    private String sousCategorieId;

    private String responsableProduction;

    private String responsablePrestation;

    private Integer delaiEnAttente;

    private Integer delaiSansSouscription;

    private List<Tarrification> tarrifications;

    private List<OptionAssurance> options;

    private List<DecesProduitCauseRestitution> decesCauseRestitution;
    private List<NormeSelection> normes;

}