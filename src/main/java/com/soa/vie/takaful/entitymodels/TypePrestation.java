package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TypePrestation extends AbstractBaseEntity {

    /**
     *
     */

    private static final long serialVersionUID = -4083770006040231209L;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column
    private String name;
    
    @ManyToOne
    private FamilleProduit famille;

}