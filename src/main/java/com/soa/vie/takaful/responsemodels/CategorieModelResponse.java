package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Branche;
import com.soa.vie.takaful.entitymodels.SousCategorie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategorieModelResponse extends AbstractBaseEntity {
    private String code;

    private String libelle;

    private String icon;

    private String type;

    private List<SousCategorie> sousCategories;

    private Branche branche;

}