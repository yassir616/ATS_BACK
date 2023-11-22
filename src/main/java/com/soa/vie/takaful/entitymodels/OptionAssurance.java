package com.soa.vie.takaful.entitymodels;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OptionAssurance extends AbstractBaseEntity {

  private static final long serialVersionUID = 1L;

  @Column(length = 50)
  private String libelle;

  @Column
  private Float min;
  
  @Column
  private Float max;                                                

	@ManyToOne
	@JsonIgnore
  private DecesProduit decesProduit;



}
