package com.soa.vie.takaful.responsemodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Acceptation;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptationTestMedicalModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private Acceptation acceptation;

    private AcceptationExamensModelResponse acceptationExamens;

    private AcceptationExaminateurModelResponse acceptationExaminateur;

    private AcceptationLaboratoireModelResponse acceptationLaboratoire;

    private AcceptationSpecialisteModelResponse acceptationSpecialiste;

    private AcceptationConseilModelResponse acceptationConseil;

    private List<HonoraireModelResponse> honoraires;

    private boolean paid = false;


}