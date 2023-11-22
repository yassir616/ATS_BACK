package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AcceptationEtape extends AbstractBaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private String etape;

    @Column
    private Date dateVerdict;

    @ManyToOne
    private Verdict verdict;

    @ManyToOne
    private AcceptationTestMedical acceptationTestMedical;

    @ManyToOne
    private AcceptationReassurance acceptationReassurance;
}