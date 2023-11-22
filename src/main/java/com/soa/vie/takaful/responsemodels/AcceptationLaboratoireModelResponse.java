package com.soa.vie.takaful.responsemodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.Auxiliaire;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptationLaboratoireModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private Date dateAnalyse;

    private Date dateReception;

    private String observations;

    private Acceptation acceptation;

    private Auxiliaire laboratoire;
}