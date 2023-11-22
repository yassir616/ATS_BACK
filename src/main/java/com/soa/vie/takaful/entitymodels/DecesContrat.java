package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "ContractId")
public class DecesContrat extends Contract {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column
    private Boolean optionFiscale;

    @Column
    private float montantCotisation;

    @Column
    private float montantFrais;

    @OneToOne(mappedBy = "contrat")
    @JsonIgnore
    private Acceptation acceptation;

    @OneToOne(mappedBy = "contrat")
    @JsonIgnore
    private Cotisation cotisation;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date datePrelevement;

    @Column
    private float tauxSurprime;

    @Column
    private float capitalAssure;

    @Column
    private Integer differe;

    @Column
    private float tauxInteret;

    @Column
    private String optionDeces;

    @Column
    private String orientation;

    @Column
    private String motifAnnulation;

    @Column
    private float montantSurprime;

    @Column
    private Float surprimeHT;

    @Column
    private Float surprimeTaxe;

    @Column
    private Float surprimeTTC;

    @Column
    private float montantTaxe;

    @Column
    private float montantEcheance;

    @Column
    private float fraisAcquisitionTTC;

    @Column
    private float fraisGestionTTC;

    @Column
    private float contributionPure;

    @Column
    private float prorata;

    @Column
    private float tauxReduction;

    @Column
    private int nombreMensualitesEtalementLaPrimeUnique;

    @Column
    private Date dateprelevementContributions;

    @ManyToOne
    private OptionAssurance optionAssurance;

    @Column
    private Boolean invaliditeOuMaladie;

    @Column
    private Boolean pensionIncapacite;

    @Column
    private Boolean suspendreAtiviteDeuxDernierAnnee;

    @Column
    private String quelleMaladieOuInvalidite;

    @Column
    private String maladiesOuOperationChirurgicaleQuandEtOu;

    @Column
    private String suspendreAtiviteDeuxDernierAnneePourquiEtTemps;

    @Column
    private boolean maladiesOuOperationChirurgicale;
}
