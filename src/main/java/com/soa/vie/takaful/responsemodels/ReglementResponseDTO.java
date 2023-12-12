package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.util.ReglementStatut;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
 public class ReglementResponseDTO {


	private String id; // table reglement

	private String libelle; // table reglement

	private ReglementStatut statut = ReglementStatut.EN_COURS; // table reglement

	private Date dateStatut; // table reglement

	private String reglementType; // table reglement

	private Integer nomFichier; // table reglement

	private String num_lot; // table reglement


	private List<PrestationHonoraireResponseDTO> prestationHonoraires;
	private List<PrestationSinistreResponseDTO> prestationSinistres;




}
