package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.Honoraire;
import com.soa.vie.takaful.entitymodels.PrestationHonoraire;
import com.soa.vie.takaful.util.ReglementStatut;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
 public class ReglementResponseDTO {


	private String libelle; // table reglement

	private ReglementStatut statut = ReglementStatut.EN_COURS; // table reglement

	private Date dateStatut; // table reglement

	private String reglementType; // table reglement

	private Integer nomFichier; // table reglement

	private String num_lot; // table reglement


	private List<PrestationHonoraireResponseDTO> honoraires;

/*

    private boolean paid = false ; // table AcceptationTestMedical

	private Date dateAnalyse; // table AcceptationExamens


	private Date dateVisite; // table AcceptationExaminateur


	private Date dateAnalyse2; // table AcceptationLaboratoire;


	private Date deteConsultation; // table AcceptationSpecialiste


	private Date dateExpertise; // table AcceptationConseil
 */


}
