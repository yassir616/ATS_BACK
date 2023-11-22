package com.soa.vie.takaful.requestmodels;

import java.util.Date;
import java.util.List;

import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.entitymodels.DetailPrestationHonoraire;
import com.soa.vie.takaful.util.PrestationStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdatePrestationHonoraireRequestModel {

    private Date datePrestation = new Date();

    private Date dateReglement;

    private Boolean isTraiter = false;

    private String modeReglement;

    private float montant;

    private float montantNet;

    private float montantIr;

    private String numeroSinistre;

    private String typePrestation;

    private String reference;

    private Auxiliaire auxiliaire;

    private PrestationStatusEnum status = PrestationStatusEnum.EN_COURS;

    private List<DetailPrestationHonoraire> detailPrestationHonoraire;
}