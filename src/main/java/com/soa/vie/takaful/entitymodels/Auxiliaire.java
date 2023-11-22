package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.soa.vie.takaful.util.VoisEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auxiliaire extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String adressPays;

	@Column
	private String adressVille;

	@Column
	@Enumerated(EnumType.STRING)
	private VoisEnum adressVois;

	@Column
	private String adressComplement;

	@Column
	private String adressCodePostal;

	@Column
	private String adressNumero;

	@Column
	private String adressComplete;

	@Column
	private String email;

	@Column
	private String faxe;

	@Column
	private Float ir;

	@Column
	private String nom;

	@Column
	private String patente;

	@Column
	private String prenom;

	@Column
	private String raisonSociale;

	@Column
	private String registreCommerce;

	@Column
	private String tel;

	@Column
	private String typePersonne;

	@Column
	private String specialite;

	@Column
	private String cin;

	@Column
	private String rib;

	@Column
	private String identifiantFiscal;

	@ManyToOne
	private TypeAuxiliaire typeAuxiliaire;

	@Column(name="typeFiscal")
    private String typeFiscal;

}
