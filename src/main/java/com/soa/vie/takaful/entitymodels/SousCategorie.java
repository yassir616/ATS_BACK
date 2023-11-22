package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class SousCategorie extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 10)
    private String code;

    @Column
    private String libelle;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categorie_sous_categorie", joinColumns = @JoinColumn(name = "sous_categorie_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "categorie_id", referencedColumnName = "id"))
    private List<Categorie> categories;

    @ManyToMany(mappedBy = "souscats")
    private List<Risque> risques;

}