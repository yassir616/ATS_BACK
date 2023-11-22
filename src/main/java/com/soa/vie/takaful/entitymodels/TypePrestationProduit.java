package com.soa.vie.takaful.entitymodels;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
public class TypePrestationProduit extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private Integer delaiDeclaration;

    @ManyToOne
    @JsonIgnore
    private Produit produit;// IdProduit
   
    @ManyToOne
    private TypePrestation typePrestation;// Idtypeprestation
    
    
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE })
	 @JoinTable(name = "typeprestation_pieceJointe",joinColumns = @JoinColumn(name = "typePrestation_id"),inverseJoinColumns = @JoinColumn(name = "pieceJointe_id") )
	    private List<PieceJointe> pieceJointe = new ArrayList<>();
}