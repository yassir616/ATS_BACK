package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DomestiqueMrb extends AbstractBaseEntity{
    private static final long serialVersionUID = 1L;

    @Column
    private String nom;
    @Column
    private String prenom;
    @Column
    private Float masseSalarialeAnnuelle;
    @Column
    private Float taux;
    
    @ManyToOne
	@JsonIgnore
    private ContratMrb contratDdomestiqueMrb;
}