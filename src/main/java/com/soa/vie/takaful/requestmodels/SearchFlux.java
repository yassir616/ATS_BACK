package com.soa.vie.takaful.requestmodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SearchFlux {
    String partenaire;

    String produit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date dateTraitement;

    String typeDeFlux;

    String etat;
}
