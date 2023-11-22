package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ProduitMrbModelResponse extends AbstractBaseEntity {


    private String code;

    private String libelle;

    
    private List<Periodicite> periodicitesMrb;

    private List<Exclusion> exclusionsProduit;

    
    private Partenaire partenaire;

    
    private String natureParticipant;

    
    private boolean assureDiffParticipant;

    
    private String natureAssure;

    
    private String natureBienAssure;

    
    private String franchiseIncendie;

    
    private String franchiseDegatEaux;

    
    private String franchiseBrisGlace;

    
    private String franchiseCatastropheNaturelles;

    
    private Float montantMaximumGarantie;

    
    private Float tauxTaxe;

    
    private Float fraisGestion;

    
    private Float tvaFraisGestion;

    
    private Integer delaiPrescription;

    
    private Integer delaiPrescriptionSansSouscription;

    //@JsonIgnore
    private List<TarificationMRB> tarificationsMrb;

}