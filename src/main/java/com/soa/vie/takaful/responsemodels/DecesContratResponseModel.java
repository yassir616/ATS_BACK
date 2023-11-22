package com.soa.vie.takaful.responsemodels;

import java.util.Date;
import java.util.List;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.entitymodels.OptionAssurance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecesContratResponseModel extends ContractResponseModel {

    /**
     * 
     */

    private Boolean optionFiscale;

    private float montantCotisation;

    private float montantFrais;

    // @JsonIgnore
    private Acceptation acceptation;

    private Cotisation cotisation;

    private Date datePrelevement;

    private Date dateEtablissement;
    private Date datePremierecheance;
    private Date dateDernierEcheance;

    private float tauxSurprime;

    private float capitalAssure;
    private float montantTaxeParafiscale;
    private Integer differe;

    private float tauxInteret;

    private String optionDeces;

    private float montantSurprime;
    private float surprimeHT;

    private float surprimeTaxe;

    private float surprimeTTC;
    private float montantTaxe;

    private float montantEcheance;

    private float fraisAcquisitionTTC;

    private float fraisGestionTTC;

    private float contributionPure;

    private float prorata;

    private float tauxReduction;

    private int nombreMensualitesEtalementLaPrimeUnique;

    private Date dateprelevementContributions;

    private OptionAssurance optionAssurance;

    private Boolean invaliditeOuMaladie;

    private Boolean pensionIncapacite;

    private Boolean suspendreAtiviteDeuxDernierAnnee;

    private String quelleMaladieOuInvalidite;

    private String maladiesOuOperationChirurgicaleQuandEtOu;

    private String suspendreAtiviteDeuxDernierAnneePourquiEtTemps;

    private Boolean maladiesOuOperationChirurgicale;

    private int numQuittance;

    private List<String> honoraires;
}
