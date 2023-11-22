package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Acceptation {
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
Date date  = new Date();
String nom;
String montantMourabaha;
String encours;
String cumul;
String duree;
String differe;
String agence;
String intermediaire;
String numeroAcceptation;
String montantCotisation;
    
}


