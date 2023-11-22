package com.soa.vie.takaful.responsemodels;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionAssuranceModelResponse extends AbstractBaseEntity {

  private String libelle;

  private Float min;
  
  private Float max;

	@JsonIgnore
  private DecesProduit decesProduit;



}
