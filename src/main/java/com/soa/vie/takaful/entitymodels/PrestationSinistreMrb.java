package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "prestationMrbId")
public class PrestationSinistreMrb extends PrestationMrb {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String numeroSinistre;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
	private Date dateDeclarationSinistre;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
	private Date dateSurvenance;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
	private Date dateResiliation;

	@Column
	private String natureSinistre;

	@Column
	private Boolean sinistreDeclare;

	@Column
	private String documentDeclaration;

	@Column
	private String documentConstatation;

	@Column
	private String numeroDocumentConstatation;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
	private Date dateReceptionDoc;

	@Column
	private String entiteReception;

	@Column
	private String referenceIntermedaire;

	@Column
	private String villeSurvenance;

	@Column
	private String province;

	@Column
	private String commune;

	@Column
	private String lieuSinistre;

	@Column
	private String cause;

	@Column
	private String circonstance;

	@Column
	private String description;

	@Column
	private Boolean remplie;

}
