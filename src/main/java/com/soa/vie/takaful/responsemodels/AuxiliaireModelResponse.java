package com.soa.vie.takaful.responsemodels;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.TypeAuxiliaire;
import com.soa.vie.takaful.util.VoisEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuxiliaireModelResponse extends AbstractBaseEntity {

	/**
	 * 
	 */

	private String adressPays;

	private String adressVille;

	@Enumerated(EnumType.STRING)
	private VoisEnum adressVois;

	private String adressComplement;

	private String adressCodePostal;

	private String adressNumero;

	private String adressComplete;

	private String email;

	private String faxe;

	private Float ir;

	private String nom;

	private String patente;

	private String prenom;

	private String raisonSociale;

	private String registreCommerce;

	private String tel;

	private String typePersonne;

	private String specialite;

	private String cin;

	private String rib;

	private TypeAuxiliaire typeAuxiliaire;

	private String identifiantFiscal;

	private String typeFiscal;

}
