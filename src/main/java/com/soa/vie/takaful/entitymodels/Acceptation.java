package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import com.soa.vie.takaful.util.AcceptationStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Acceptation extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String code;

    @OneToOne
    private DecesContrat contrat;

    @Column
    private String numeroDeDossier;

    @Column
    private String observation;

    @Column
    @Enumerated(EnumType.STRING)
    private AcceptationStatus status;

    @Column
    private boolean honoraire;

    @Column
    private float encours;

    @Column
    private String verdict;

    @Column
    private float cumul;

}