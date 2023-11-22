package com.soa.vie.takaful.responsemodels;


import com.soa.vie.takaful.entitymodels.Prestation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PrestationRachatTotalModelResponse extends Prestation {

    private Date dateDepart;

    private float montantBrutRachatTotal;

    private float montantNetRachatTotal;

    private float ir;



}
