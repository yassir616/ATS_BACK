package com.soa.vie.takaful.responsemodels;


import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TypeAuxiliaireModelResponse extends AbstractBaseEntity {

	/**
	 * 
	 */
	private String code;
	private String libelle;
	


}
