package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LettreAcceptationAvecSurprime {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date = new Date();
    private String intermediaire;
    private String nomPrenom;
    private String agence;
    private String numeroAcceptation;
    private String montantFinancement;
    private String encours;
    private String cumul;
    private String duree;
    private String differe;
    private String tauxSurprime;
    private String surprimeTTC;

}
