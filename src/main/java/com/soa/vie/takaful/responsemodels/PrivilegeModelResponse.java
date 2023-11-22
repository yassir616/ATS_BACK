package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PrivilegeModelResponse extends AbstractBaseEntity {

    /**
     *
     */
    private String name;

    @JsonIgnore
    private List<Role> roles;
}