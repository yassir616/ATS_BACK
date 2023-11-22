package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Honoraire extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String code;

	@Column
	private Float montantHonoraire;

	@Column(length = 50)
	private String libelle;

	@Column(length = 50)
	private String intituele;

	@ManyToOne
	private TypeAuxiliaire typeAuxiliaireHon;

}
