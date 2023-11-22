package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProduitModelResponse extends AbstractBaseEntity {

    /**
     *
     */
    private String code;// abreviation

    private String libelle;

    private String numeroHomologation;

    
    private Date dateHomologation;
    

    private Date dateModification  = new Date();

    
    private Float fraisGestion;

    
    private Float tvaFraisGestion;
    
    private String descriptif;

    private String pieceJointe;

    
    private Float seuilPrestation;

    
    private String produitType;

    
    private Float taxe;

    
    private Float montantAccessoire;

    
    private String numeroCompte;

    
    private String libelleCompte;

    
    private String codeCompte;
    
    
    private Float fraisAcquisition;
     
    
    private Float tvaFraisAcquisition;
     
    private List<Exclusion> exclusions;

    
    private List<Periodicite> periodicites;

    
    private List<PointVente> pointVentes;

    private Partenaire partenaire;

    
    private Categorie categorie;

    
    private Risque risque;
    
    
    private List<Commission> commissions;
    
    private List<TypePrestationProduitModelResponse> prestations;
    

}