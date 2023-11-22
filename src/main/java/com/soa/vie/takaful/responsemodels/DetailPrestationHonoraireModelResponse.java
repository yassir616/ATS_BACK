package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.AcceptationTestMedical;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class DetailPrestationHonoraireModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private AcceptationTestMedical acceptationTestMedical;

    private float montantHonoraire;

    private float montantHonoraireInitial;

}