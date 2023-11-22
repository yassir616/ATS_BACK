package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BrancheModelResponse extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    private String code;

    private String libelle;

    private String icon;

}