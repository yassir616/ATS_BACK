package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Encaissement extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String numEncaissement;

	@Column
	private float montantEncaissement;

	@Column
	private String numReference;

	@Column
	private String modeEncaissement;

	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateEncaissement;

	@Column
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateEtablissement;

	@Column
	private float montantTaxe;

	@Column
	private float montantTaxeParafiscale;

	@Column
	private float accessoire;

	@Column
	private float montantQuittance;

	@Column
	private Float montantEmission;

	@Column
	private Boolean flagBordereau = false;

	@ManyToOne
	private Cotisation cotisation;

	@ManyToOne
	private CompteBancaire compteBancaire;

	@ManyToOne
	@JoinColumn(name = "bordereau_encaissement_id")
	private BordereauxEncaissement bordereauEncaissement;

	

}
