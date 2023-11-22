package com.soa.vie.takaful.entitymodels;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SinistreMrb extends AbstractAutoIdBaseEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateDeclarationSinistre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateSurvenance;

    private Date heureSurvenance;

    private String natureSinistre;

    private Boolean sinistreDeclare;

    private String documentDeclaration;

    private String documentConstatation;

    private String numeroDocumentConstatation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateReceptionDoc;

    private String entiteReception;

    private String referenceIntermedaire;

    private String villeSurvenance;

    private String province;

    private String commune;

    private String lieuSinistre;

    private String cause;

    private String circonstance;

    private String description;

    private String raisonSocialeSocietaire;
    private String numSocietaire;
    private String adresseSocietaire;
    private String telephoneSocietaire;
    private String villeSocietaire;
    private String faxSocietaire;
    private String emailSocietaire;
    private String cinSociataire;
    private String periodicite;
    private String nomVictime;
    private String prenomVictime;
    private String cinVictime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateEffet;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateEcheance;
    private String police;
    private String codeCaps;
    private String codeSite;
    private String natureIntermediaire;
    private float caiptal;
    private float franchise;
    private List<ContratGarantie> garanties;

}
