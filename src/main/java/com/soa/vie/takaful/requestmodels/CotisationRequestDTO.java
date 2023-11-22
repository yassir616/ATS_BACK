package com.soa.vie.takaful.requestmodels;


import java.util.Date;

import com.soa.vie.takaful.util.EtatCotisation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CotisationRequestDTO {
    //Cotisation Data
    private String id;
    private Date datePrelevement;
    private float montantCotisation;
    private float montantTaxe;
    private float solde;
    private Float montantTaxeParaFiscale;
    private String annulation;
    private float montantAccessoire;
    private float montantTTC;
    private float fraisAcquisitionTTC;
    private float fraisGestionTTC;
    private float contributionPure;
    private Float capitalAssure;
    private Date dateEtablissement;
    private int exercice;
    private String numeroLot;
    private int numeroQuittance;
    private Date creationDateCotisation;
    private EtatCotisation etatCotisation;
    //Contract Data
    private String numeroContrat;
    private Date dateEffet;
    private Date dateEcheance;
    //Rest Of Data
    private String nomAssuree;
    private String prenomAssuree;
    private String nomSouscripteur;
    private String prenomSouscripteur;
    private String produitLibelle;
}
