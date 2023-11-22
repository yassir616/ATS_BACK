package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.util.SexeEnum;
import com.soa.vie.takaful.util.SituationFamilialeEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "personneHistoriqueId")
public class PersonnePhysiqueHistorique extends PersonneHistorique {

    private static final long serialVersionUID = 1L;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column(nullable = false, length = 1)
    private boolean isProspect;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateNaissance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SexeEnum sexe;

    @Column(nullable = false)
    private String cin;

    @Column
    private String rib;

    @Column
    private String numTiers;

    @Column
    private String vivant;

    @Column
    private String compteTakaful;

    @Column
    private String matricule;

    @Column
    private String controleTerrorisme;

    @Column
    private String terrorisme;

    @Column
    private Integer nombreEnfants;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SituationFamilialeEnum situationFamiliale;

    @ManyToOne
    @JoinColumn
    private Profession profession;

    @Column(nullable = false)
    private String nationalite;
}
