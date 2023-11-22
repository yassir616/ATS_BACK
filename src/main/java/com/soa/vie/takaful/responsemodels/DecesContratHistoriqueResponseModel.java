package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.ContratHistorique;
import com.soa.vie.takaful.entitymodels.OptionAssurance;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DecesContratHistoriqueResponseModel extends ContratHistorique {

    /**
     *
     */
    private Boolean optionFiscale;

    private float montantCotisation;

    private float montantFrais;

    @JsonIgnore
    private Acceptation acceptation;

    private Date datePrelevement;

    private float tauxSurprime;

    private float capitalAssure;

    private Integer differe;

    private float tauxInteret;

    private String optionDeces;

    private float montantSurprime;

    private float montantTaxe;

    private float prorata;

    private float tauxReduction;

    private OptionAssurance optionAssurance;

}

