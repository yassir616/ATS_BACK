package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Periodicite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductMRB {
    private String code;
    
    private String libelle;

    private List<Periodicite> periodicitesMrb;
    
    private List<Exclusion> exclusionsProduit;
    
    private String natureParticipant;
    
    private boolean assureDiffParticipant;
    
    private String natureAssure;
    
    private String franchiseIncendie;
    
    private String franchiseDegatEaux;
    
    private String franchiseBrisGlace;
    
    private String franchiseCatastropheNaturelles;
    
    private Float montantMaximumGarantie;
    
    private Float tauxTaxe ;
     
    private Float fraisGestion;
    
    private Float tvaFraisGestion;
    
    private Integer delaiPrescription;
    private String partenaireId;
    private Integer delaiPrescriptionSansSouscription;


}
