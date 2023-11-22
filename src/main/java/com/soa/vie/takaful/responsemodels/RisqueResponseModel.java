package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.SousCategorie;
import com.soa.vie.takaful.util.BrancheTypeEnum;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RisqueResponseModel extends AbstractBaseEntity {

	/**
	 * 
	 */
	private String code;

	private String libelle;

	private String bundle;

	private String icon;

	private String theme;

	private String name;

	private List<SousCategorie> souscats;

	private BrancheTypeEnum brancheType;


}
