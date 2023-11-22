package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLettreRejet {

    private String intermediaire;
    private String agence;
    private String nomParticipant;
    private String numeroAcceptation;
    private String montantFinancement;
    private String encours;
    private String cumul;
    private String duree;
    private String differe;
    private String date;

}
