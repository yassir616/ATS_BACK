package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.util.EtatCotisation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cotisation extends AbstractBaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date datePrelevement;

    @Column
    private float montantCotisation;

    @Column
    @Enumerated(EnumType.STRING)
    private EtatCotisation etatCotisation;

    @Column
    private int numQuittance;

    @Column
    private float solde;

    @Column
    private float montantTaxe;

    @Column
    private Float montantTaxeParaFiscale;
    @Column
    private String annulation;
    @Column
    private float montantAccessoire;

    @Column
    private float montantTTC;

    @Column
    private String cotisationType;

    @Column
    private float fraisAcquisitionTTC;

    @Column
    private float fraisGestionTTC;

    @Column
    private float contributionPure;

    @Column
    private Float capitalAssure;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateEtablissement;

    @Column
    private int exercice;

    @ManyToOne
    private Contract contrat;

    @Column
    private String numeroLot;

    @Column
    private int flagBatch;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateEmission;


}
