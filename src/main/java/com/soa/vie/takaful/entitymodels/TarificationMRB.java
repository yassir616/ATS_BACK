package com.soa.vie.takaful.entitymodels;
import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity

public class TarificationMRB extends AbstractBaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column
	private String natureBienAssure;

	/* @Column
	private Float tauxContribution;
	@Column
	private Integer ageMax;
	
	@Column
	private Integer ageMin;
	
	@Column
	private Float valeurMax;
	
	@Column
	private Float valeurMin; */
	@Column
	private Float valeurBatiment;
	
	@Column
	private Float contenu;

	@Column
	private Float primeNet;
	
	@ManyToOne
	@JsonIgnore
	private ProduitMrb produitMrb;
}
