package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReglementRequest {

	private String libelle;
	private Date dateStatut;
	private String reglementType;

}
