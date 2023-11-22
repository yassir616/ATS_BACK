package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationReassurance;
import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.entitymodels.Verdict;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateallAcceptationTestKindModelRequest {

    private String motif;

    private Date dateExpertise;

    private Date dateReassurance;

    private String observation;

    private Acceptation acceptation;

    private AcceptationReassurance acceptationReassurance;

    private String observationVerdict;

    private String observationsVerdict;

    private float tauxSurmoralite;

    private float tauxSurprime;

    private String examen_complementaire;

    private Auxiliaire medecin;

    private Verdict verdict;

    private String specialite;

    private Auxiliaire specialiste;

    private Date deteConsultation;

    private String natureTest;

    private Date dateAnalyse;

    private Date dateReception;

    private Date dateVisite;

    private String observations;

    private Auxiliaire laboratoire;

    private String test;
    private float montantSurprime;
    private float surprimeHT;
    private float surprimeTaxe;
    private float surprimeTTC;

}