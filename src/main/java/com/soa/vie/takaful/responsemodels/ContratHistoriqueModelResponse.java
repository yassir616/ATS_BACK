package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.*;
import com.soa.vie.takaful.util.ContratStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class ContratHistoriqueModelResponse extends AbstractBaseEntity {

    /**
     *
     */
    private ProduitModelResponse produit;

    
    private Avenant avenantId;

    
    private Personne souscripteur;

    
    private PersonnePhysique assure;

    
    private PointVente pointVente;

    
    private Periodicite periodicite;


    private Date dateEffet;

    private Date dateEcheance;

    
    private Integer dureeContrat;

    private String numeroContrat;

    
    private Date dateResiliation;

    
    private String contratType;

    
    private int optionEmission;

    
    private Date dateEtablissement;

    
    private ContratStatus status;



}