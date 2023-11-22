package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Contract;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BeneficiareModelResponse extends AbstractBaseEntity {

    /**
     * 
     */
    private String nomBeneficiare;

    private float pourcentage;

    private Contract contrat;

}
