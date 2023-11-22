package com.soa.vie.takaful.responsemodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Agenda;
import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.Role;
import com.soa.vie.takaful.util.AccountStateEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakafulUserResponseModel extends AbstractBaseEntity{

  /**
   *
   */

  private String firstName;

  private String lastName;

  private String email;

  private String encryptePassword;

  private List<Role> roles;

  private List<PointVente> pointVentes;

  private List<Agenda> events;

  private AccountStateEnum status;

  public void deleteUser() {
    this.status = AccountStateEnum.DELETED;
  }
}
