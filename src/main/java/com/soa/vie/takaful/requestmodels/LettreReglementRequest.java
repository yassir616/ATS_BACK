package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LettreReglementRequest {
    private String cin;
    // private float montantHonoraireAssure;
    private String num_sinistre;
    private String rib;
    private String benificiaire;
    private String assure;
    private float montantBrut;
    private float montantTaxe;
    private float montantNet;
    private String nature;
    private String total;
    private String partenaire;
    private String agence;
    private String adresse;
    private String adresseAuxiliaire;
    private String date;
    private String referencePartenaire;
    private String num_lot;
    private String code_part;
    private String date_visite;
}
