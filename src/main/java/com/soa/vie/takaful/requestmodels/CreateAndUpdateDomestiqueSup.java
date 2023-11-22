package com.soa.vie.takaful.requestmodels;


import com.soa.vie.takaful.entitymodels.ContratMrb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateDomestiqueSup {
	    private String nom;
	    private String prenom;
	    private Float masseSalarialeAnnuelle;
	    private Float taux;
	    private ContratMrb domestiqueMrb;


}
