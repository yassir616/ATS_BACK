package com.soa.vie.takaful.requestmodels;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.entitymodels.DomestiqueMrb;
import com.soa.vie.takaful.entitymodels.GarantieMrb;
import com.soa.vie.takaful.entitymodels.Personne;
import com.soa.vie.takaful.entitymodels.ProduitMrb;
import com.soa.vie.takaful.util.CategorieMrb;
import com.soa.vie.takaful.util.TypeProprieteMrb;
import com.soa.vie.takaful.util.ValeurMrb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContratMrb {

    private String numeroContrat;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date dateEffet;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    private Date dateEcheance;

    private String coassurance;

    private String taciteReconduction;

    private Personne souscripteur;

    private Personne assure;

    private ProduitMrb produit;

    private ValeurMrb valeurBatiment;

    private CategorieMrb categorie;

    private Float valeurContenu;

    private TypeProprieteMrb typePropriete;

    private String siuationRisque;

    private Float reduction;

    private DomestiqueMrb domestique;

    private List<GarantieMrb> garantieMrb;

}