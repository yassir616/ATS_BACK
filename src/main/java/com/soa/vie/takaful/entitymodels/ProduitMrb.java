package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProduitMrb extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String code;

    @Column(length = 100)
    private String libelle;

    @ManyToMany
    @JoinTable(name = "produit_periodicite_mrb", joinColumns = @JoinColumn(name = "produitmrb_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "periodicite_id", referencedColumnName = "id"))
    private List<Periodicite> periodicitesMrb;

    @ManyToMany
    @JoinTable(name = "produit_exclusion_mrb", joinColumns = @JoinColumn(name = "produitmrb_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "exclusion_id", referencedColumnName = "id"))
    private List<Exclusion> exclusionsProduit;

    @ManyToOne
    private Partenaire partenaire;

    @Column
    private String natureParticipant;

    @Column
    private boolean assureDiffParticipant;

    @Column
    private String natureAssure;

    @Column
    private String natureBienAssure;

    @Column
    private String franchiseIncendie;

    @Column
    private String franchiseDegatEaux;

    @Column
    private String franchiseBrisGlace;

    @Column
    private String franchiseCatastropheNaturelles;

    @Column
    private Float montantMaximumGarantie;

    @Column
    private Float tauxTaxe;

    @Column
    private Float fraisGestion;

    @Column
    private Float tvaFraisGestion;

    @Column
    private Integer delaiPrescription;

    @Column
    private Integer delaiPrescriptionSansSouscription;

    @OneToMany(mappedBy = "produitMrb", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<TarificationMRB> tarificationsMrb;

}