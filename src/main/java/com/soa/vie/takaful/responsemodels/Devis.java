package com.soa.vie.takaful.responsemodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Devis {

    private String pointVente;
    private String dateNaissance;
    private String duree;
    private String periodicite;
    private String capital;
    private String montantParticipationHT;
    private String taxe;
    private String montantParticipationTTC;
    private String differe;
    private String risque;
    private String produit;

    public Devis(String nomPrenom, String produit, String pointVente, String dateNaissance, String capital,
            String duree, String differe, String periodicite, String montantParticipationHT, String taxe,
            String montantParticipationTTC, String risque) {
        this.pointVente = pointVente;
        this.dateNaissance = dateNaissance;
        this.capital = capital;
        this.differe = differe;
        this.duree = duree;
        this.periodicite = periodicite;
        this.montantParticipationHT = montantParticipationHT;
        this.taxe = taxe;
        this.montantParticipationTTC = montantParticipationTTC;
        this.risque = risque;
        this.produit = produit;
    }

}
