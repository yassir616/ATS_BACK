package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContratGarantie extends AbstractBaseEntity{

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="contrat_id")
    private ContratMrb contratMrb;
    @ManyToOne
    @JoinColumn(name="garantieMrb_id")
    private GarantieMrb garantieMrb;
    @Column
    private Float capital;
    @Column
    private Float franchise;
    @Column
    private Float cotisationHTAnnuelle;
    @Column
    private Float cotisationHTProrata;
    @Column
    private Float taxeProrata;
    @Column
    private Float ttcProrata;

    
}