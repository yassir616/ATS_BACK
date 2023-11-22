package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRetraiteContrat {

    private Float capital;
    private Integer duree;
    private Float montantContributionInitiale;
    private Float montantContributionPeriodique;
    private Integer nombrePeriodicite;
    private String typeAvenantId;
}
