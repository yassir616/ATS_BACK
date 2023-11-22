package com.soa.vie.takaful.responsemodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.Verdict;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptationReassuranceModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private Date dateReassurance;

    private Date dateReception;

    private String observation;

    // private AcceptationModelResponse acceptationModelResponse;
    private Acceptation acceptation;

    private Verdict verdict;

    private String observationVerdict;

    private String motif;

    private float tauxSurprime;

}