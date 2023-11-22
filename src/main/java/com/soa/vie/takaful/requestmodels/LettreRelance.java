package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LettreRelance {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date date = new Date();
    String nomPrenom;
    String ville;
    String libelle;
    String pointVenteVille;
    String numeroSinistre;
    String raisonSocial;
    String numeroContrat;
    String commentaire;
}
