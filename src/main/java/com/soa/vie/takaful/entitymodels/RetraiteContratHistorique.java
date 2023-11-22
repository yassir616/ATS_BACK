package com.soa.vie.takaful.entitymodels;

import com.soa.vie.takaful.util.BenificiareCasDeces;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "ContractHistoriqueId")
public class RetraiteContratHistorique extends ContratHistorique {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column
    private boolean deductibiliteFiscale;

    @Column
    private boolean souscripteurIsAssure;

    @Column
    private boolean attributionIrrevocable;

    @Column
    @Enumerated(EnumType.STRING)
    private BenificiareCasDeces benificiareCasDeces;

    @Column
    private float montantContributionInitiale;

    @Column
    private float montantContributionPeriodique;

    @Column
    @ManyToMany
    private List<BeneficiaireEnDeces> beneficiaireEnDeces;

    @Column
    private Integer nombrePeriodicite;

}
