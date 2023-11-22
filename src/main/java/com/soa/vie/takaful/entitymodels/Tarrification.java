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
public class Tarrification extends AbstractBaseEntity {

  private static final long serialVersionUID = 1L;

  @Column
  private Integer ageMin;

  @Column
  private Integer ageMax;

  @Column
  private Float capitalMin;

  @Column
  private Float capitalMax;

  @Column
  private Integer dureeMax;

  @Column
  private Integer dureeMin;

  @Column
  private Float differeMax;

  @Column
  private Float differeMin;

  // taux = taux annulelle
  @Column
  private Float taux;
  @Column
  private Float tauxMensuelle;

  @Column
  private Float tauxSemestrielle;

  @Column
  private Float tauxTrimestrielle;
  
  @Column
  private Float forfait;

  @ManyToOne
  @JsonIgnore
  private DecesProduit decesProduit;

}
