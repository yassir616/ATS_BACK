package com.soa.vie.takaful.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrestationSinistreResponseDTO {

    private String id;

    private String numeroSinistre;
    private String modeReglement; // table prestation
    private float montant; // table prestion
    private float montantNet; // table prestion
    private float montantIr; // table prestion
    private String typePrestation; // table prestion
    private String nomAssure; // table personne_physique
    private String prenomAssure; // table personne_physique
    private String codePartenaire; // table paetenaire
    private String numeroCompte; // table partenaire
    private String raisonSocial; // table partenaire

    private List<ExculusionResponseDTO> exculusions;

    public PrestationSinistreResponseDTO(String id, String numeroSinistre, String modeReglement, float montant, float montantNet, float montantIr, String typePrestation, String nomAssure, String prenomAssure, String codePartenaire, String numeroCompte, String raisonSocial) {
        this.id = id;
        this.numeroSinistre = numeroSinistre;
        this.modeReglement = modeReglement;
        this.montant = montant;
        this.montantNet = montantNet;
        this.montantIr = montantIr;
        this.typePrestation = typePrestation;
        this.nomAssure = nomAssure;
        this.prenomAssure = prenomAssure;
        this.codePartenaire = codePartenaire;
        this.numeroCompte = numeroCompte;
        this.raisonSocial = raisonSocial;
    }


}
