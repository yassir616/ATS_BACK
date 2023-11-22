package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.Avenant;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.OptionAssurance;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.Personne;
import com.soa.vie.takaful.entitymodels.PersonnePhysique;
import com.soa.vie.takaful.entitymodels.PointVente;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateContratHistorique {
    private OptionAssurance optionAssurance;

    private DecesProduit produit;

    private Personne souscripteur;

    private PersonnePhysique assure;

    private PointVente pointVente;

    private Periodicite preiodicite;

    private Acceptation acceptation;

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

    private Date dateEffet;

    private Date dateEcheance;

    private int dureeContrat;

    private String numeroContrat;

    private Date dateResiliation;

    private String contratType;

    private int optionEmission;

    private Date dateEtablissement;

    private float encours;

    private float cumul;
    
    private Avenant avenantId;

}
