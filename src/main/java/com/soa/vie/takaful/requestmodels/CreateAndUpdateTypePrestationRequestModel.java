package com.soa.vie.takaful.requestmodels;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateTypePrestationRequestModel {
    
    private String code;

    private String libelle;

    private String name;

    private String familleId;

}