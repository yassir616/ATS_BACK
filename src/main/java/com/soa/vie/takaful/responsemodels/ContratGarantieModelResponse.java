package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.GarantieMrb;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContratGarantieModelResponse extends AbstractBaseEntity {


    private ContratMrbModelResponse contratMrb;

    private GarantieMrb garantieMrb;

    private Float capital;

    private Float franchise;

    private Float cotisationHTAnnuelle;

    private Float cotisationHTProrata;

    private Float taxeProrata;

    private Float ttcProrata;

    
}