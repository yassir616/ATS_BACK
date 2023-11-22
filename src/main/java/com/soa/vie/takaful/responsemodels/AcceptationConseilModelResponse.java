package com.soa.vie.takaful.responsemodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.entitymodels.Verdict;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptationConseilModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private String motif;

    private Date dateExpertise;

    private String observation;

    private Acceptation acceptation;

    private String observationsVerdict;

    private float tauxSurmoralite;

    private float tauxSurprime;

    private Auxiliaire medecin;

    private Verdict verdict;

}