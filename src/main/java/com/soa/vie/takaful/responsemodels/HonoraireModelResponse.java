package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.TypeAuxiliaire;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class HonoraireModelResponse extends AbstractBaseEntity {

	/**
	 * 
	 */

	private String code;

	
	private Float montantHonoraire;

	private String libelle;

	private String intituele;

	private TypeAuxiliaire typeAuxiliaireHon;

}
