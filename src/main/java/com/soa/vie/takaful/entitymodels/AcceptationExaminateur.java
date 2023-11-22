package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AcceptationExaminateur extends AbstractBaseEntity{


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String motif;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateVisite;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateReception;

    @Column
    private String observation;

    @ManyToOne
    private Acceptation acceptation;

    @Column
    private String observationsVerdict;

    @ManyToOne
    private Auxiliaire medecin;

    @ManyToOne
    private Verdict verdict;
}