package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSinistre {

	private String contratId;

	private String natureSinistre;

	private Date dateSurvenance;
	
	private String typePrestation;

	private Date dateDeclarationSinistre;

}
