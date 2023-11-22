package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.entitymodels.DetailPrestationHonoraire;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrestationHonoraireResponseModel extends PrestationResponseModel {

    /**
     *
     */
    private String reference;

    private Auxiliaire auxiliaire;

    private List<DetailPrestationHonoraire> detailPrestationHonoraire;

}