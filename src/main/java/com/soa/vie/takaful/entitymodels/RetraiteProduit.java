package com.soa.vie.takaful.entitymodels;

import com.soa.vie.takaful.util.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "produitId")
@AllArgsConstructor
@NoArgsConstructor
public class RetraiteProduit extends Produit {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ManyToMany
    @JoinTable( name = "retraitProduit_PoolInvestissment", joinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pool_investissment_id", referencedColumnName = "id"))
    private List<PoolInvestissment> poolInvestissment;

    @Column
    @Enumerated(EnumType.STRING)
    private NatureFiscale natureFiscale;

    @Column
    private float revenuGlobal;

    @Column
    private Integer dureeMinimalSouscription;

    @Column
    private boolean renouvellementContratTaciteReconduction;

    @Column
    private float montantMinContribution;

    @Column
    private float tauxRendementAvantImposition;

    @Column
    @Enumerated(EnumType.STRING)
    private ModeGestion modeGestion;

    @Column
    private float fraisFixeWakalabilIstithmar;

    @Column
    private float surperformanceWakalabilIstithmar;

    @Column
    private float profitMoudaraba;

    @Column
    @Enumerated(EnumType.STRING)
    ModeCalculCapitalConstitue modeCalculCapitalConstitue;

    @Column
    private boolean rachatTotal;

    @Column
    private Integer dureeMinimalSouscriptionAvantRachatTotal;

    @Column
    private boolean conditionDisciplinaireTotale;

    @Column
    @Enumerated(EnumType.STRING)
    NatureConditionDisciplinaire natureConditionDisciplinaireTotale;

    @Column
    private float valeurConditionDisciplinaireTotale;

    @Column
    private boolean rachatPartiel;

    @Column
    private boolean conditionDisciplinairePartiel;

    @Column
    private Integer dureeMinimalSouscriptionAvantRachatPartiel;

    @Column
    @Enumerated(EnumType.STRING)
    NatureConditionDisciplinaire natureConditionDisciplinairePartiel;

    @Column
    private float valeurConditionDisciplinairePartiel;

    @Column
    private float maximumMontantRachatPartiel;

    @Column
    private Integer nombreMaximumRachatPartiel;



    @Column
    @Enumerated(EnumType.STRING)
    private RegimeFiscal regimeFiscal;


}
