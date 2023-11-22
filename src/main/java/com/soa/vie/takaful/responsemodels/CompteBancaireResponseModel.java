package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.PointVente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompteBancaireResponseModel extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String code;
	
    private String rib;
	
    private PointVente pointVente;

}
