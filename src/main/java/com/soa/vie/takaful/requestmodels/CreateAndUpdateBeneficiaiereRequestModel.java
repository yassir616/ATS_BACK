package com.soa.vie.takaful.requestmodels;

import com.soa.vie.takaful.entitymodels.Contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateBeneficiaiereRequestModel {

    private String nomBeneficiare;
    
    private float pourcentage;
    
    private Contract contrat;

} 
    
    


 

   


