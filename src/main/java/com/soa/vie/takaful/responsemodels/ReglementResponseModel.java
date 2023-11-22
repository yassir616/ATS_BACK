package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.PrestationHonoraire;
import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.util.ReglementStatut;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReglementResponseModel extends AbstractBaseEntity {

	/**
	 * 
	 */
	private String libelle;

	private ReglementStatut statut = ReglementStatut.EN_COURS;

	private Date dateStatut;

	private String reglementType;

	private Integer nomFichier;

	private List<PrestationSinistre> prestations;

	private List<PrestationHonoraire> honoraires;

	private String num_lot;

}
