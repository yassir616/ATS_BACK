package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NormeSelection extends AbstractBaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Column
    private Float capitalMin;

    @Column
    private Float capitalMax;

    @Column(nullable = false)
    private Integer ageMin;

    @Column(nullable = false)
    private Integer ageMax;

    @ManyToOne
    @JsonIgnore
    private DecesProduit decesProduit;
 

    @ManyToMany
    @JoinTable(name = "examens_medicaux", joinColumns = @JoinColumn(name = "norme_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "honoraire_id", referencedColumnName = "id"))
    private List<Honoraire> honoraires;

    

    

}