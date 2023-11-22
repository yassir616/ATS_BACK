package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BeneficiaireAgenceModelResponseModelResponse extends BeneficiaireSinistreModelResponse {

	/**
	 * 
	 */

	    private PrestationSinistre sinistre;
	  
	    private PointVente pointVente;

}
