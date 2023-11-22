package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Exclusion extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    // @Lob
    @Column(nullable = false)
    private String exclusionNom;

    @Column
    private String famille;

    @ManyToMany(mappedBy = "exclusions")
    @JsonIgnore
    private List<Produit> produits;

    @ManyToMany(mappedBy = "exclusionsContrat")
    @JsonIgnore
    private List<ContratMrb> contrats;

    @ManyToMany(mappedBy = "exclusionsProduit")
    @JsonIgnore
    private List<ProduitMrb> produitMrb;

}
