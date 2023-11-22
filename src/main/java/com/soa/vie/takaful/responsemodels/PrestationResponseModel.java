package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.util.PrestationStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PrestationResponseModel extends AbstractBaseEntity {

    /**
     *
     */
    private Date datePrestation ;

    private Date dateReglement ;

    private Boolean isTraiter = false;

    private String modeReglement;

    private float montant;

    private String numeroSinistre;

    private float montantNet;

    private float montantIr;

    private String typePrestation;

    private Contract contrat;

    private PrestationStatusEnum status = PrestationStatusEnum.EN_COURS;

}