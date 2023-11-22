package com.soa.vie.takaful.requestmodels;


import com.soa.vie.takaful.entitymodels.*;
import com.soa.vie.takaful.util.BenificiareCasDeces;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateUpdateRetraiteContrat extends Contract {

    private boolean deductibiliteFiscale;

    private boolean souscripteurIsAssure;

    private boolean attributionIrrevocable;

    private BenificiareCasDeces benificiareCasDeces;

    private float montantContributionInitiale;

    private float montantContributionPeriodique;

    private List<BeneficiaireEnDeces> beneficiaireEnDeces;

    private Integer nombrePeriodicite;

    private String typeAvenantId;

    private Date datePrelevement;

}
