package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.util.EtatCotisation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CotisationModelResponse extends AbstractBaseEntity {

    /**
     * 
     */

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
    private float capitalAssure;

    private Contract contrat;

    private String numeroLot;

}
