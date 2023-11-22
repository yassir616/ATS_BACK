package com.soa.vie.takaful.requestmodels;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.soa.vie.takaful.util.VoisEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdatePointVente {

    private String abb;

    private String codeCaps;

    private String codeInterne;

    private String codeResponsable;

    private String ice;

    private String libelle;

    private String nomResponsable;

    private String patente;

    private String logo;

    private String partenairepvId;

    private String typePointVenteId;

    private String secteurActiviteId;

    private String adressPays;

    private String adressVille;

    @Enumerated(EnumType.STRING)
    private VoisEnum adressVois;

    private String adressComplement;

    private String adressCodePostal;

    private String adressNumero;

    private String adressComplete;
    private String telephone;
    private String email;

    private String code;
    private String rib;
}