package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.*;
import com.soa.vie.takaful.util.ContratStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContractResponseModel extends AbstractBaseEntity {

    /**
     * 
     */

    private Produit produit;

    private Personne souscripteur;

    private PersonnePhysiqueResponseModel assure;

    private PointVente pointVente;

    private Periodicite periodicite;

    private String numeroDossierFinancement;

    private boolean compteJoint;

    private String orientation;

    private Long numeroDeSimulation;

    private Date dateEffet;

    private Date dateEcheance;

    private Integer dureeContrat;

    private String numeroContrat;

    private Date dateResiliation;

    private String contratType;

    private int optionEmission;
    private Integer numOrdrePrelevement;

    private Date dateEtablissement;

    private ContratStatus status;

    private Date dateDeSignature;

    private Date dateLimiteCoverture;

    private Date datePremierecheance;

    private Date dateDernierEcheance;

}
