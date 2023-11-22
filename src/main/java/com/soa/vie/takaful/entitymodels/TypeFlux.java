package com.soa.vie.takaful.entitymodels;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class TypeFlux extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String libelle;

    @Column
    private String code;
}
