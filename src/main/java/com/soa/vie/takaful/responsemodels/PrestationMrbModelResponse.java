package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.ContratMrb;
import com.soa.vie.takaful.util.PrestationStatusEnum;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class PrestationMrbModelResponse extends AbstractBaseEntity {

	
	    private Date datePrestation ;

	    
	    private Date dateReglement ;

	    private Boolean isTraiter = false;

	    
	    private String modeReglement;

	    
	    private float montant;

	    private String numeroSinistre;

	    
	    private float montantNet;

	    
	    private String typePrestation;


	    private ContratMrb contratMrb;

	    private PrestationStatusEnum status = PrestationStatusEnum.EN_COURS;


}
