package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.DecesContrat;
import com.soa.vie.takaful.util.AcceptationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptationModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private String code;

    private DecesContrat contrat;

    private String numeroDeDossier;

    private String observation;

    private AcceptationStatus status;

    private boolean honoraire;

    private float encours;

    private float cumul;

    private String verdict;

}