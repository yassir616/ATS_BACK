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
public class Periodicite extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 10)
    private String abb;

    @Column(nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "periodicites")
    @JsonIgnore
    private List<Produit> produits;

    @ManyToMany(mappedBy = "periodicitesMrb")
    @JsonIgnore
    private List<ProduitMrb> produitsMrb;

}