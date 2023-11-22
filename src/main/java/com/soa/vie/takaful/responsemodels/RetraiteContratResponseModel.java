package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.entitymodels.*;
import com.soa.vie.takaful.util.BenificiareCasDeces;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RetraiteContratResponseModel extends ContractResponseModel{


    private boolean deductibiliteFiscale;

    private boolean souscripteurIsAssure;

    private boolean attributionIrrevocable;

    private BenificiareCasDeces benificiareCasDeces;

    private float montantContributionInitiale;

    private float montantContributionPeriodique;

    private List<BeneficiaireEnDeces> beneficiaireEnDeces;

    private List<PrestationRachatTotalModelResponse> prestationRachatTotals;

    private Integer nombrePeriodicite;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date datePrelevement;

}
