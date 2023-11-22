package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter 
public class UpdateSinistreMrb {
	    private String numeroSinistre;
		private Date dateDeclarationSinistre;
		private Date dateSurvenance;
		private Date dateResiliation;
		private String natureSinistre;
		private Boolean sinistreDeclare;
		private String documentDeclaration;
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
