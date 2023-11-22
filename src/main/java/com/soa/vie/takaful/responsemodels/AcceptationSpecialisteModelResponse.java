package com.soa.vie.takaful.responsemodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Acceptation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptationSpecialisteModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private String specialite;

    private Date deteConsultation;

    private String natureTest;

    private String observations;

    private Acceptation acceptation;

    private AuxiliaireModelResponse specialiste;

}