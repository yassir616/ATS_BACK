package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DetailPrestationHonoraire extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private AcceptationTestMedical acceptationTestMedical;

    @Column(nullable = false)
    private float montantHonoraire;

    @Column
    private float montantHonoraireInitial;


}