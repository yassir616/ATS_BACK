package com.soa.vie.takaful.requestmodels;

import java.util.Date;
import java.util.List;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.OptionAssurance;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.Personne;
import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.util.ContratStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUpdateDecesContrat {

    private OptionAssurance optionAssurance;

    private String numeroDossierFinancement;

    private Long numeroDeSimulation;

    private boolean compteJoint;

    private Personne souscripteur;

    private PersonnePhysique assure;

    private Periodicite preiodicite;

    private Acceptation acceptation;

    private String orientation;
    private Boolean optionFiscale;

    private float montantCotisation;

    private float montantFrais;

    private Date datePrelevement;

    private boolean prelevementSource;

    private float tauxSurprime;

    private float capitalAssure;

    private Integer differe;

    private float tauxInteret;

    private String optionDeces;

    private float montantSurprime;

    private float montantTaxe;

    private float prorata;

    private float tauxReduction;

    private float montantTaxeParafiscale;
    private float montantAccessoire;

    private Date dateEffet;

    private Date dateEcheance;

    private int dureeContrat;

    private String numeroContrat;

    private Date dateResiliation;

    private String contratType;

    private int optionEmission;

    private float surprimeHT;

    private float surprimeTaxe;

    private float surprimeTTC;

    private Date dateEtablissement;
    private Date datePremierecheance;
    private Date dateDernierEcheance;

    private float encours;

    private float cumul;

    private float montantEcheance;

    private int nombreMensualitesEtalementLaPrimeUnique;

    private Date dateprelevementContributions;

    private boolean invaliditeOuMaladie;

    private boolean pensionIncapacite;

    private boolean suspendreAtiviteDeuxDernierAnnee;

    private String quelleMaladieOuInvalidite;

    private String maladiesOuOperationChirurgicaleQuandEtOu;

    private String suspendreAtiviteDeuxDernierAnneePourquiEtTemps;

    private boolean maladiesOuOperationChirurgicale;

    private ContratStatus status;

    private float fraisAcquisitionTTC;

    private float fraisGestionTTC;

    private float contributionPure;
    private float seuilExaminateur;
    private float seuilReassurance;
    private float seuilConseil;
    private String idProd;
    private String idPointVente;
    private String idPeriodicite;
    private int age;

    private List<String> honoraires;

}
