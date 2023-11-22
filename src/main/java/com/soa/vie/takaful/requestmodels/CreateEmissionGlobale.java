package com.soa.vie.takaful.requestmodels;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmissionGlobale {

	private String numeroLot;
    private Float montantTTC;
    private Float montantCotisation;
    private Float montantTaxe;
    private Float montantTaxeParaFiscale;
    private Float solde;
    private Float fraisAcquisitionTTC;
    private Float fraisGestionTTC;

}

