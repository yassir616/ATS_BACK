package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarrificationResponseModel extends AbstractBaseEntity {

  private boolean isFound = true;

  private Integer ageMin;

  private Integer ageMax;

  private Float capitalMin;

  private Float capitalMax;

  private Integer dureeMax;

  private Integer dureeMin;

  private Float differeMax;

  private Float differeMin;

  // taux = taux annulelle
  private Float taux;

  private Float tauxMensuelle;

  private Float forfait;

  @JsonIgnore
  private DecesProduit decesProduit;

}
