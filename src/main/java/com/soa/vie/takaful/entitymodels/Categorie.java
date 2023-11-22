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
public class Categorie extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(length = 50)
    private String icon;

    @Column(length = 100)
    private String type;

    @ManyToMany(mappedBy = "categories")
    private List<SousCategorie> sousCategories;

    @ManyToOne
    private Branche branche;

}