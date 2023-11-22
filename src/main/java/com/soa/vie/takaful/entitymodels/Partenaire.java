package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.soa.vie.takaful.util.BrancheTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Partenaire extends AbstractBaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(length = 31)
    private String typePartenaire;
    
    @Column
    private String siegeSocial;
    
    @Column
    private String code;
 
    @Column
    private String numeroCompte;
    
    @Column
    private String raisonSocial;

    @Column
    private String fraisAcquisition;//commission

    @Column
    private String tva;
    
    @Column
    private Integer telephone;

    @Column
    @Enumerated(EnumType.STRING)
    private BrancheTypeEnum brancheType;
}
