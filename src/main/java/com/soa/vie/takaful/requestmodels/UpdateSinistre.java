package com.soa.vie.takaful.requestmodels;

import java.util.Date;
import java.util.List;

import com.soa.vie.takaful.entitymodels.Exclusion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSinistre {
    private String commentaire;
    private Date dateDeclarationSinistre;
    private Date dateSurvenance;
    private Integer dureeAvantDeclaration;
    private Integer dureeAvantSurvenance;
    private Float montantRegler;
    private Float crd;
    private String natureSinistre;
    private Integer nbrEcheanceImpaye;
    private Float tauxInvalidite;
    private String numeroCompte;
    // private String reglementPartiel;
    private List<Exclusion> exclusions;
    private String numeroSinistre;
    private String modeReglement;
    private Boolean beneficiaireAgenceBancaire;
    private String pointVente;
    private Boolean remplie;
    private String causeSinistre;
    private Date dateCreation;

}