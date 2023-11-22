package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestModel {

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private List<Role> roles;

    private List<PointVente> pointVentes;

}
