package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Categorie;
import com.soa.vie.takaful.entitymodels.Risque;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter

public class SousCategorieModelResponse extends AbstractBaseEntity {


    private String code;

    private String libelle;

    private List<Categorie> categories;

    private List<Risque> risques;

}