package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationConseil;
import com.soa.vie.takaful.entitymodels.AcceptationExamens;
import com.soa.vie.takaful.entitymodels.AcceptationExaminateur;
import com.soa.vie.takaful.entitymodels.AcceptationLaboratoire;
import com.soa.vie.takaful.entitymodels.AcceptationSpecialiste;
import com.soa.vie.takaful.entitymodels.Honoraire;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAcceptationTestMedicalModelRequest {

    private Acceptation acceptation;

    private AcceptationExamens acceptationExamens;

    private AcceptationExaminateur acceptationExaminateur;

    private AcceptationLaboratoire acceptationLaboratoire;

    private AcceptationSpecialiste acceptationSpecialiste;

    private AcceptationConseil acceptationConseil;

    private List<Honoraire> honoraires;

}