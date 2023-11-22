package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.entitymodels.AcceptationTestMedical;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePrestationHonoraireDetailRequestModel {

    private AcceptationTestMedical acceptationTestMedical;

    private float montantHonoraire;

    private float montantHonoraireInitial;

}