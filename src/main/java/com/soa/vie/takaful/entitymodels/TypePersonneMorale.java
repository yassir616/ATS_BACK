package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypePersonneMorale extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = true, length = 10)
    private String code;

    @Column(unique = true)
    private String libelle;

}