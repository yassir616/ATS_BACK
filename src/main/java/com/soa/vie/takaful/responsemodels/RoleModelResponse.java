package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Privilege;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleModelResponse extends AbstractBaseEntity {

  /**
   *
   */
  private String name;

  @JsonIgnore
  private List<TakafulUser> users;

  private List<Privilege> privileges;
}