package com.soa.vie.takaful.entitymodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PoolInvestissment extends AbstractBaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String libelle;
}
