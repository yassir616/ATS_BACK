package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "prestationId")
public class PrestationHonoraire extends Prestation {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String reference;

    @ManyToOne
    private Auxiliaire auxiliaire;

    @OneToMany
    private List<DetailPrestationHonoraire> detailPrestationHonoraire;

}