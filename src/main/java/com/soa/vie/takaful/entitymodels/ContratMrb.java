package com.soa.vie.takaful.entitymodels;

import java.util.List;

import javax.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "ContractId")
public class ContratMrb extends Contract {

    private static final long serialVersionUID = 1L;

    // @Column
    // private String numeroDossierFinancement;
    @Column
    private String typeBatiment;
    @Column
    private String adresseBatiment;
    @Column
    private String numeroTitreFoncier;
    @Column
    private String superficie;
    @Column
    private String description;
    // @Column
    // @Temporal(TemporalType.DATE)
    // @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    // private Date dateEffet;
    // @Column
    // @Temporal(TemporalType.DATE)
    // @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    // private Date dateEcheance;
    @Column
    private String coassurance;
    @Column
    private String taciteReconduction;

    @ManyToOne
    private ProduitMrb produitMrb;

    @Column
    //@Enumerated(EnumType.STRING)
    private String valeurBatiment;
    @Column
    //@Enumerated(EnumType.STRING)
    private String categorie;
    @Column
    private Float valeurContenu;
    @Column
    //@Enumerated(EnumType.STRING)
    private String typePropriete;
    @Column
    private String situationRisque;
    @Column
    private Float reduction;

    @Column
    private Float primeNette;

    @Column
    private Float primeTTC;
    @Column
    private Float primeHT;
    @Column
    private Float primeEvcat;
    @Column
    private Float FESC;
    @Column
    private Float taxe;
    @Column
    private Float taxeParaFiscale;
    @Column
    private Float prorata;
    @Column
    private Float prorataTTC;

    @OneToMany(mappedBy = "contratDdomestiqueMrb", cascade = CascadeType.ALL)
    private List<DomestiqueMrb> domestique;

    @OneToMany(mappedBy = "contratMrb", cascade = CascadeType.ALL)
    private List<ContratGarantie> contratGarantie;

    @ManyToMany
    @JoinTable(name = "contrat_exclusions_mrb", joinColumns = @JoinColumn(name = "contratmrb_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "exclusion_id", referencedColumnName = "id"))
    private List<Exclusion> exclusionsContrat;
}