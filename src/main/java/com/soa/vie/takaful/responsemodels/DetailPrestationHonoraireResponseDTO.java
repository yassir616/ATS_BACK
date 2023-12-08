package com.soa.vie.takaful.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailPrestationHonoraireResponseDTO {


    private String id;
    private float montantHonoraire; //table detail_prestation
    private float montantHonoraireInitial; // table detail_prestation
    private boolean paid = false; // table AcceptationTestMedical
    private Date dateAnalyseExamens; // table AcceptationExamens
    private Date dateVisite; // table AcceptationExaminateur
    private Date dateAnalyseLaboratoire; // table AcceptationLaboratoire;
    private Date deteConsultation; // table AcceptationSpecialiste
    private Date dateExpertise; // table AcceptationConseil

}
