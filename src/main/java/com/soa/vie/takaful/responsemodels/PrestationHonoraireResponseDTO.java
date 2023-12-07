package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.Honoraire;
import com.soa.vie.takaful.entitymodels.PrestationHonoraire;
import com.soa.vie.takaful.util.ReglementStatut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrestationHonoraireResponseDTO {
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

/*
    private float montant; // table prestion


    private float montantNet; // table prestion


    private float montantIr; // table prestion


    private String typePrestation; // table prestion


    private String nomAssure; // table personne_physique

    private String prenomAssure; // table personne_physique


    private float montantHonoraire; //table detail_prestation


    private float montantHonoraireInitial; // table detail_prestation


    private String codePartenaire; // table paetenaire


    private String numeroCompte; // table partenaire


    private String raisonSocial; // table partenaire */
}
