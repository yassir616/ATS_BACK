package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.soa.vie.takaful.util.EtatCotisation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CreateAndUpdateCotisation {
    private Date datePrelevement;
    private float montantCotisation;
    private EtatCotisation etatCotisation;
    private String numQuittance;
    private float solde;
    private float montantTaxe;
    private float montantAccessoire;
    private float montantTTC;
    private float contributionPure;
    private float fraisAcquisitionTTC;
    private float fraisGestionTTC;
    private String cotisationType;
    private Date dateEtablissement;
    private int exercice;
    private String contrat;
    private float montantTaxeParaFiscale;
    private int flagBatch;
    private Date dateEmission;

}
