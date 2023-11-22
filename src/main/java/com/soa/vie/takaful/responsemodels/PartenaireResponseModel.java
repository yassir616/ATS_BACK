package com.soa.vie.takaful.responsemodels;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.util.BrancheTypeEnum;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PartenaireResponseModel extends AbstractBaseEntity {

    /**
     * 
     */
    private String typePartenaire;
    
    private String siegeSocial;
    
    private String code;
 
    private String numeroCompte;
    
    private String raisonSocial;

    private String fraisAcquisition;//commission

    private String tva;
    
    private Integer telephone;

    @Enumerated(EnumType.STRING)
    private BrancheTypeEnum brancheType;
}
