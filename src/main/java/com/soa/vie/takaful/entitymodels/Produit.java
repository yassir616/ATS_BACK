package com.soa.vie.takaful.entitymodels;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Produit extends AbstractBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(length = 50)
    private String code;// abreviation

    @Column
    private String libelle;

    @Column(length = 50)
    private String numeroHomologation;

    @Column(columnDefinition = "datetime")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateHomologation;

    @Column(columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private Date dateModification = new Date();

    @Column
    private Float fraisGestion;

    @Column
    private Float tvaFraisGestion;

    @Column(columnDefinition = "TEXT")
    private String descriptif;

    @Column(length = 50)
    private String pieceJointe;

    @Column
    private Float seuilPrestation;

    @Column
    private String produitType;

    @Column
    private Float taxe;

    @Column
    private Float montantAccessoire;

    @Column
    private String numeroCompte;

    @Column
    private String libelleCompte;

    @Column
    private String codeCompte;

    @Column
    private String taxeParaFiscale;

    @Column
    private String numeroPolice;

    @Column
    private String responsableProduction;

    @Column
    private String responsablePrestation;

    @Column
    private Float fraisAcquisition;

    @Column
    private Float tvaFraisAcquisition;

    @ManyToMany
    @JoinTable(name = "produit_exclusion", joinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "exclusion_id", referencedColumnName = "id"))
    private List<Exclusion> exclusions;

    @ManyToMany
    @JoinTable(name = "produit_periodicite", joinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "periodicite_id", referencedColumnName = "id"))
    private List<Periodicite> periodicites;

    @ManyToMany
    @JoinTable(name = "produit_pointvente", joinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pointvente_id", referencedColumnName = "id"))
    private List<PointVente> pointVentes;

    @ManyToOne
    private Partenaire partenaire;

    @ManyToOne
    private Categorie categorie;

    @ManyToOne
    private SousCategorie sousCategorie;

    @ManyToOne
    private Risque risque;

    @ManyToOne
    private FamilleProduit famille;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Commission> commissions;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<TypePrestationProduit> prestations;

}