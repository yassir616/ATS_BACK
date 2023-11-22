package com.soa.vie.takaful.entitymodels;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.util.BenificiareCasDeces;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "ContractId")
public class RetraiteContrat extends Contract {
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

    @Column
    @OneToMany
    private List<PrestationRachatTotal> prestationRachatTotals;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date datePrelevement;
}
