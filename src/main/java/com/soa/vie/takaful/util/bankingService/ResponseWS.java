package com.soa.vie.takaful.util.bankingService;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.entitymodels.AbstractAutoIdBaseEntity;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.Profession;
import com.soa.vie.takaful.entitymodels.SecteurActivite;
import com.soa.vie.takaful.entitymodels.TypePersonneMorale;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWS extends AbstractAutoIdBaseEntity {

    public int status = 0;

    private Produit produit;

    private String codeClient;

    private String montantFinnancement;

    private String nom;

    private String prenom;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateNaissance;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateEcheance;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateEffet;
    private String dureeContrat;

    private String partenaire;

    private String rib;

    private String cin;

    private String refDossierFinnancement;

    private String sexe;

    private String situationFamiliale;

    private Profession profession;

    private String nationalite;

    private String typePersonne;

    private String abb;

    private String raisonSociale;

    private String patente;

    private String ice;

    private TypePersonneMorale typePersonneMorale;

    private SecteurActivite secteurActivite;

    private String adressVille;

    private String adressPays;

    private String adressCodePostal;

    private String adressNumero;

    private float differe;

    private float encours;

    private String capitalAssure;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date datePrelevement;

    private Periodicite preiodicite;

}
