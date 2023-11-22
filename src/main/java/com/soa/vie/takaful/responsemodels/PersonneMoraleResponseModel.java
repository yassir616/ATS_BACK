package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePersonneMorale;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonneMoraleResponseModel extends PersonneResponseModel {

    private String id;
    private String abb;

    private String raisonSociale;

    private String patente;

    private String ice;

    private String code;

    private TypePersonneMorale typePersonneMorale;

    private SecteurActivite secteurActivite;
    private String rib;

}