package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.util.BrancheTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateRisqueRequestModel {
    
    
    private String code;

    private String libelle;

    private String bundle;

    private String icon;

    private String theme;

    private String name;
    
    private BrancheTypeEnum brancheType;

}
