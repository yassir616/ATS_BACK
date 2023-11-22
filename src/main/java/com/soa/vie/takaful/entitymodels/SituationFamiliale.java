package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SituationFamiliale extends AbstractBaseEntity{

    private static final long serialVersionUID = 1L;

    @Column(nullable = false,length = 10)
    private String code;

    @Column
    private String libelle;
    
}