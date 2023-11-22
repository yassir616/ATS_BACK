package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Beneficiare extends AbstractBaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String nomBeneficiare;

    @Column
    private float pourcentage;

    @ManyToOne
    private Contract contrat;

}
