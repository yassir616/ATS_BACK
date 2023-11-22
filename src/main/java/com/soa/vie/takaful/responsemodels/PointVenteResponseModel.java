package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePointVente;
import com.soa.vie.takaful.util.VoisEnum;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class PointVenteResponseModel extends AbstractBaseEntity {

    private String abb;

    private String libelle;

    private String codeCaps;

    private String codeInterne;

    private String patente;

    private String ice;

    private String codeResponsable;

    private String nomResponsable;

    private String logo;

    private PartenaireResponseModel partenairepv;

    private TypePointVente typePointVente;

    private SecteurActivite secteurActivite;

    @JsonIgnore
    private List<Produit> produits;

    private String adressPays;

    private String adressVille;

    @Enumerated(EnumType.STRING)
    private VoisEnum adressVois;

    private String adressComplement;

    private String adressCodePostal;

    private String adressNumero;

    private String adressComplete;
    private String email;
    private String telephone;


}