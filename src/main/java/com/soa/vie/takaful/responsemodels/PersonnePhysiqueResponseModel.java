package com.soa.vie.takaful.responsemodels;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.vie.takaful.entitymodels.Profession;
import com.soa.vie.takaful.util.SexeEnum;
import com.soa.vie.takaful.util.SituationFamilialeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonnePhysiqueResponseModel extends PersonneResponseModel {

    private String nom;

    private String prenom;

    private boolean isProspect;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateNaissance;

    private SexeEnum sexe;

    private String cin;

    private String rib;

    private String numTiers;

    private String vivant;

    private String compteTakaful;

    private String matricule;

    private String controleTerrorisme;

    private String terrorisme;

    private Integer nombreEnfants;

    private SituationFamilialeEnum situationFamiliale;

    private Profession profession;

    private String nationalite;

	private String identite;

}