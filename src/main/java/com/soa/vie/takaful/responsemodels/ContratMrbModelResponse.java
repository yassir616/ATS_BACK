package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.entitymodels.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter
@Setter
public class ContratMrbModelResponse extends ContractResponseModel {

    private String numeroSimulation;

    private String numeroContrat;

    private String numeroDossierFinancement;

    private String typeBatiment;

    private String adresseBatiment;

    private String numeroTitreFoncier;

    private String superficie;

    private String description;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateEffet;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateEcheance;

    private String coassurance;

    private String taciteReconduction;

    private ProduitMrbModelResponse produitMrb;

    private String valeurBatiment;

    private String categorie;

    private Float valeurContenu;
    private Integer dureeContrat;

    private String typePropriete;

    //private String situationRisque;

    private Float reduction;

    private Periodicite periodicite;
    private Float primeNette;

    private Float primeTTC;
    private Float primeHT;
    private Float primeEvcat;
    private Float FESC;
    private Float taxe;
    private Float taxeParaFiscale;
    private Float prorata;
    private Float prorataTTC;

    private List<DomestiqueMrbModelResponse> domestique;

    private List<ContratGarantieModelResponse> contratGarantie;

    private List<Exclusion> exclusionsContrat;
}