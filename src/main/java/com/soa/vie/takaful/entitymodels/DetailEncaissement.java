package com.soa.vie.takaful.entitymodels;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetailEncaissement extends AbstractBaseEntity{
    private Float montantEncaissement;
    private int cumEncaissement;
    private String compteBancaire;
    private String exercice;
    private String numeroLot;
}
