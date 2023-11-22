package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Branche extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(length = 50)
    private String icon;

}