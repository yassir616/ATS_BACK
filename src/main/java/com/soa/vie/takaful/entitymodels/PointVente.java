package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.util.VoisEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PointVente extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String abb;

    @Column
    private String libelle;

    @Column
    private String codeCaps;

    @Column
    private String codeInterne;

    @Column
    private String patente;

    @Column
    private String ice;

    @Column
    private String codeResponsable;

    @Column
    private String nomResponsable;

    @Column
    private String logo;

    @ManyToOne
    private Partenaire partenairepv;

    @ManyToOne
    private TypePointVente typePointVente;

    @ManyToOne
    private SecteurActivite secteurActivite;

    @ManyToMany(mappedBy = "pointVentes")
    @JsonIgnore
    private List<Produit> produits;

    @Column
    private String adressPays;

    @Column
    private String adressVille;

    @Column
    @Enumerated(EnumType.STRING)
    private VoisEnum adressVois;

    @Column
    private String adressComplement;

    @Column
    private String adressCodePostal;

    @Column
    private String adressNumero;

    @Column
    private String adressComplete;

    @Column
    private String telephone;

    @Column
    private String email;
}