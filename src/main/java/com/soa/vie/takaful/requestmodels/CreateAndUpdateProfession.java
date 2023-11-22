package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateProfession extends AbstractBaseEntity {

    private String libelle;
}
