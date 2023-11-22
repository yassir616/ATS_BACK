package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Identite")
public class Identite extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true)
    private String libelle;
}