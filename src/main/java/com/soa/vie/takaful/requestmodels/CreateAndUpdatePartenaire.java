package com.soa.vie.takaful.requestmodels;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.soa.vie.takaful.util.BrancheTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdatePartenaire {
	
    private String code;
    
    private String siegeSocial;

    private String numeroCompte;

    private String raisonSocial;
    
    private String typePartenaire;

    private String fraisAcquisition;

    private String tva;
    
    private Integer telephone;

    @Enumerated(EnumType.STRING)
    private BrancheTypeEnum brancheType;
    
	
}