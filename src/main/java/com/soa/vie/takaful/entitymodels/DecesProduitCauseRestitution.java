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
 public class DecesProduitCauseRestitution extends AbstractBaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(nullable = true)
    private Integer status;

    @ManyToOne
    @JsonIgnore
    private DecesProduit deces;
    
    @ManyToOne
    private Restitution restitution;

    @ManyToOne
    private CauseRestitution causeRestitution;

	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE })
	 @JoinTable(name = "decesProduitCauseRestitution_pieceJointe",joinColumns = @JoinColumn(name = "decesProduitCauseRestitution_id"),inverseJoinColumns = @JoinColumn(name = "pieceJointe_id") )
	    private List<PieceJointe> pieceJointes = new ArrayList<>();

	


	
	
	 
}