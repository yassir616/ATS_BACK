package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "ContractHistoriqueId")
public class DecesContratHistorique extends ContratHistorique {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Column
    private Boolean optionFiscale;

    @Column
    private float montantCotisation;

    @Column
    private float montantFrais;

    @OneToOne(mappedBy = "contrat")
    @JsonIgnore
    private Acceptation acceptation;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date datePrelevement;

    @Column
    private float tauxSurprime;

    @Column
    private float capitalAssure;

    @Column
    private Integer differe;

    @Column
    private float tauxInteret;

    @Column
    private String optionDeces;

    @Column
    private float montantSurprime;

    @Column
    private float montantTaxe;

    @Column
    private float prorata;

    @Column
    private float tauxReduction;

    @ManyToOne
    private OptionAssurance optionAssurance;

}

