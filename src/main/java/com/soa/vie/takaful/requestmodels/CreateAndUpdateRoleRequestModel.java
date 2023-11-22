package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Privilege;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateRoleRequestModel {

    private String name;

    private List<Privilege> privileges;

}