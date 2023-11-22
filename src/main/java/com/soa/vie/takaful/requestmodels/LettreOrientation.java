package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LettreOrientation {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date date = new Date();
    String ville;
    String nomPrenom;
    String cin;
    String montantFinancement;
    String montantEncours;
    String montantCumul;
    String nomExaminateur;
    String adresseExaminateur;
    String telephone;
    String email;
    String duree;
    String differe;
    String taux;
    String agence;
    String intermediaire;
    String norme;
    String dateNaissance;
    String pointVenteVille;
    String examinateurId;
    String codeAcceptation;

}
