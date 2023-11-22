package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDevisMrb {

    private String nomPrenom;
    private String pointVente;
    private String dateNaissance;
    private String valeurBatiment;
    private String periodicite;
    private String typeBatiment;
    private String primeNette;
    private String primeHT;
    private String primeTTC;
    private String primeEvcat;
    private String produit;
    private String taxe;
    private String prorata;
    private String prorataTTC;
}
