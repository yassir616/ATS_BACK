package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.ContratGarantie;
import com.soa.vie.takaful.entitymodels.ProduitMrb;
import com.soa.vie.takaful.util.GarantieMrbType;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GarantieMrbModelResponse extends AbstractBaseEntity {
    
    private String code;
    
    private String libelle;
    
    private GarantieMrbType typegarantie;
    
    private Float taux;
    
    private Float taxe;

    private ProduitMrb produitMrb;

    private List<ContratGarantie> contratGaranties;
}