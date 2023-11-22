package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.Acceptation;

import com.soa.vie.takaful.entitymodels.Verdict;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReassuranceModel {


    private String motif;

    private Date dateReassurance;

    private String observation;

    private Acceptation acceptation;

    private String observationVerdict;

    private float tauxSurprime;

    private Verdict verdict;

    private Date dateReception;


}