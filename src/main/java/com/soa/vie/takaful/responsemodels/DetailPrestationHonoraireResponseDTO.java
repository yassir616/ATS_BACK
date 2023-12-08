package com.soa.vie.takaful.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetailPrestationHonoraireResponseDTO {


    private String id;
    private String acceptationMedicalId;
    private float montantHonoraire; //table detail_prestation
    private float montantHonoraireInitial; // table detail_prestation
    private boolean paid = false; // table AcceptationTestMedical
    private Date dateAnalyseExamens; // table AcceptationExamens
    private Date dateVisite; // table AcceptationExaminateur
    private Date dateAnalyseLaboratoire; // table AcceptationLaboratoire;
    private Date deteConsultation; // table AcceptationSpecialiste
    private Date dateExpertise; // table AcceptationConseil
    private List<HonoraireResponseDTO> honoraires;

    // Constructeur de la requete
    public DetailPrestationHonoraireResponseDTO(String id,String acceptationMedicalId, float montantHonoraire, float montantHonoraireInitial, boolean paid, Date dateAnalyseExamens, Date dateVisite, Date dateAnalyseLaboratoire, Date deteConsultation, Date dateExpertise) {
        this.id = id;
        this.acceptationMedicalId= acceptationMedicalId;
        this.montantHonoraire = montantHonoraire;
        this.montantHonoraireInitial = montantHonoraireInitial;
        this.paid = paid;
        this.dateAnalyseExamens = dateAnalyseExamens;
        this.dateVisite = dateVisite;
        this.dateAnalyseLaboratoire = dateAnalyseLaboratoire;
        this.deteConsultation = deteConsultation;
        this.dateExpertise = dateExpertise;
    }
}
