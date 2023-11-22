package com.soa.vie.takaful.util.bankingService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AkhdarData {

    private Long dealId;
    private String accountNumber;
    private String rib;
    private String nomPersonne;
    private String prenomPersonne;
    private String dateNaisssance;
    private String typePersonne;
    private String nationalite;
    private String situationFamiale;
    private Double encours;
    private String profession;
    private String sexe;
    private String cin;
    private String adressePays;
    private String adresseVille;
    private Integer dureeContrat;
    private float differe;
    private String dateEcheance;
    private String dateEffet;
    private String ice;
    private String codeProduit;
    private String periodicite;
    private String datePrelevement;
    private Double capitalAssure;

}
