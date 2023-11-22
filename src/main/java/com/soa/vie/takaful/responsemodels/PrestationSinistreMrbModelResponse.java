package com.soa.vie.takaful.responsemodels;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
public class PrestationSinistreMrbModelResponse extends PrestationMrbModelResponse {

	/**
	 * 
	 */

	private String numeroSinistre;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateDeclarationSinistre;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateSurvenance;

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateResiliation;

	private String natureSinistre;

	private Boolean sinistreDeclare;

	private String documentDeclaration;

	private String documentConstatation;

	private String numeroDocumentConstatation;

	private Date dateReceptionDoc;

	private String entiteReception;

	private String referenceIntermedaire;

	private String villeSurvenance;

	private String province;

	private String commune;

	private String lieuSinistre;

	private String cause;

	private String circonstance;

	private String description;

	private Boolean remplie;

}
