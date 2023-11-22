package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUpdateCatAndSubCatRequestModel {

    private String code;

    private String libelle;

    private String icon;

    private String type;

    private String brancheId;
}
