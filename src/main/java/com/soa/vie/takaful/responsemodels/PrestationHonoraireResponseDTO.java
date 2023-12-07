package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.DetailPrestationHonoraire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrestationHonoraireResponseDTO {

    private String id;    //  table prestation_honoraire
    private String reference; // table prestation_honoraire
    private String adressPaysAuxiliaire; // table auxiliare
    private String adressVilleAuxiliaire; // table auxiliare
    private String adressComplementAuxiliaire; // table auxiliare
    private String emailAuxiliaire; // table auxiliare
    private String nomAuxiliaire; // table auxiliare
    private String patenteAuxiliaire; // table auxiliare
    private String prenomAuxiliaire; // table auxiliare
    private String typePersonneAuxiliaire; // table auxiliare
    private String specialiteAuxiliaire; // table auxiliare
    private String cinAuxiliaire; // table Auxiliaire
    private String ribAuxiliaire; // table Auxiliaire
    private String typeFiscalAuxiliaire; // table Auxiliaire
    private String codeTypeAuxiliaire; // table TypeAuxiliaire
    private String libelleTypeAuxiliaire; // TypeAuxiliaire
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

    private List<DetailPrestationHonoraireResponseDTO> detailPrestationHonoraires;

    public PrestationHonoraireResponseDTO(String id, String reference, String adressPaysAuxiliaire, String adressVilleAuxiliaire, String adressComplementAuxiliaire, String emailAuxiliaire, String nomAuxiliaire, String patenteAuxiliaire, String prenomAuxiliaire, String typePersonneAuxiliaire, String specialiteAuxiliaire, String cinAuxiliaire, String ribAuxiliaire, String typeFiscalAuxiliaire, String codeTypeAuxiliaire, String libelleTypeAuxiliaire, String modeReglement, float montant, float montantNet, float montantIr, String typePrestation, String nomAssure, String prenomAssure, String codePartenaire, String numeroCompte, String raisonSocial) {
        this.id = id;
        this.reference = reference;
        this.adressPaysAuxiliaire = adressPaysAuxiliaire;
        this.adressVilleAuxiliaire = adressVilleAuxiliaire;
        this.adressComplementAuxiliaire = adressComplementAuxiliaire;
        this.emailAuxiliaire = emailAuxiliaire;
        this.nomAuxiliaire = nomAuxiliaire;
        this.patenteAuxiliaire = patenteAuxiliaire;
        this.prenomAuxiliaire = prenomAuxiliaire;
        this.typePersonneAuxiliaire = typePersonneAuxiliaire;
        this.specialiteAuxiliaire = specialiteAuxiliaire;
        this.cinAuxiliaire = cinAuxiliaire;
        this.ribAuxiliaire = ribAuxiliaire;
        this.typeFiscalAuxiliaire = typeFiscalAuxiliaire;
        this.codeTypeAuxiliaire = codeTypeAuxiliaire;
        this.libelleTypeAuxiliaire = libelleTypeAuxiliaire;
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
