package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.util.ContratStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class ContratHistorique extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Produit produit;

    @ManyToOne
    private Avenant avenantId;

    @ManyToOne
    private Personne souscripteur;

    @ManyToOne
    private PersonnePhysiqueHistorique assure;

    @ManyToOne
    private PointVente pointVente;

    @ManyToOne
    private Periodicite periodicite;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateEffet;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateEcheance;

    @Column
    private Integer dureeContrat;

    @Column(nullable = false)
    private String numeroContrat;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateResiliation;

    @Column
    private String contratType;

    @Column
    private int optionEmission;

    @Column
    private Date dateEtablissement;

    @Column
    @Enumerated(EnumType.STRING)
    private ContratStatus status;

}