package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "produitId")
public class DecesProduit extends Produit {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column
    private Integer delaiResiliation;

    @Column
    private Integer delaiPreavis;

    @Column
    private Float seuilExaminateur;

    @Column
    private Float seuilConseil;

    @Column
    private Integer calculCRD;

    @Column
    private Float seuilReassurance;

    @Column
    private Integer dureeMax;

    @Column
    private Integer dureeMin;

    @Column
    private Integer differeMin;

    @Column
    private Integer differeMax;

    @Column
    private Integer ageMin;

    @Column
    private Integer ageMax;

    @Column(length = 100)
    private String codeExoneration;

    @Column
    private String typeTarif;

    @Column
    private String codificationProduit;

    @Column
    private Integer ageEcheance;

    @Column
    private Integer ageVisite;

    @Column
    private String tauxRabais;

    @Column
    private Integer ageMaxEligibilite;

    @Column
    private String echeanceImpayees;

    @Column
    private Integer delaiEnAttente;

    @Column
    private Integer delaiSansSouscription;

    @OneToMany(mappedBy = "decesProduit", cascade = CascadeType.ALL)
    private List<Tarrification> tarrifications;

    @OneToMany(mappedBy = "decesProduit", cascade = CascadeType.ALL)
    private List<OptionAssurance> options;

    @OneToMany(mappedBy = "deces", cascade = CascadeType.ALL)
    private List<DecesProduitCauseRestitution> decesCauseRestitution;

    @OneToMany(mappedBy = "decesProduit", cascade = CascadeType.ALL)
    private List<NormeSelection> normes;

}