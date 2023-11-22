package com.soa.vie.takaful.requestmodels;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdatePrestationRachatTotal {

    private String contratId;

    private Date dateDepart;

    private float montantBrutRachatTotal;

    private float montantNetRachatTotal;

    private float ir;

}
