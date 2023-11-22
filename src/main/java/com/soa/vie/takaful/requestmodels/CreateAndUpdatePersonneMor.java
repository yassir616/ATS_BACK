package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePersonneMorale;
import com.soa.vie.takaful.util.VoisEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdatePersonneMor {

	private String logo;

	private String abb;

	private String raisonSociale;

	private String patente;

	private String ice;

	private String code;

	private TypePersonneMorale typePersonneMorale;

	private SecteurActivite secteurActivite;

	private String adressPays;

	private String adressVille;

	private VoisEnum adressVois;

	private String adressComplement;

	private String adressCodePostal;

	private String adressNumero;

	private String adressComplete;

	private Integer numeroDeTelephone;
	private String rib;

}