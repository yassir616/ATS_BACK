package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.entitymodels.PersonnePhysique;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDecesContrat {

	private Float capital;

	private Integer duree;

	private Integer differe;

	private Float montantSurprime;

	private Float montantTaxe;

	private Float prorata;

	private Float montantCotisation;

	private Integer tauxSurprime;

	private Integer tauxReduction;
	private float seuilExaminateur;
	private float seuilReassurance;
	private float seuilConseil;
	private String typeAvenantId;
	private String Status;
	private Date datePrelevement;
	private Date dateEffet;
	private String motifAnnulation;
	private boolean invaliditeOuMaladie;

	private boolean pensionIncapacite;

	private boolean suspendreAtiviteDeuxDernierAnnee;

	private boolean maladiesOuOperationChirurgicale;
	private PersonnePhysique Assure;
     
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateCreation = new Date();
}
