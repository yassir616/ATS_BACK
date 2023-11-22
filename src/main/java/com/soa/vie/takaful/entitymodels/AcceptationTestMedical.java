package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AcceptationTestMedical  extends AbstractBaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Acceptation acceptation;
    
    @ManyToOne
    private AcceptationExamens acceptationExamens;
    
    @ManyToOne
    private AcceptationExaminateur acceptationExaminateur;
    
    @ManyToOne
    private AcceptationLaboratoire acceptationLaboratoire;

    @ManyToOne
    private AcceptationSpecialiste acceptationSpecialiste;
    
    @ManyToOne
    private AcceptationConseil acceptationConseil;

    @ManyToMany
    private List<Honoraire> honoraires;

    @Column
    private boolean paid = false ;

}