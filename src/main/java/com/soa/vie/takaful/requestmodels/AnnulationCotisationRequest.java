package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.util.EtatCotisation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnulationCotisationRequest {
    private String id;

    private Date datePrelevement;

    private float montantCotisation;

    private EtatCotisation etatCotisation;

    private int numQuittance;

    private float solde;

    private float montantTaxe;

    private Float montantTaxeParaFiscale;

    private float montantAccessoire;

    private float montantTTC;

    private String cotisationType;

    private float fraisAcquisitionTTC;

    private float fraisGestionTTC;

    private float contributionPure;

    private Date dateEtablissement;

    private int exercice;

    private Contract contrat;
}
