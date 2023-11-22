package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.soa.vie.takaful.util.GarantieMrbType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GarantieMrb extends AbstractBaseEntity{

    private static final long serialVersionUID = 1L;

    @Column
    private String code;
    @Column
    private String libelle;
    @Column
    @Enumerated(EnumType.STRING)
    private GarantieMrbType typegarantie;
    @Column
    private Float taux;
    @Column
    private Float taxe;

    @ManyToOne
    private ProduitMrb produitMrb;

    @OneToMany(mappedBy = "garantieMrb")
    private List<ContratGarantie> contratGaranties;
}