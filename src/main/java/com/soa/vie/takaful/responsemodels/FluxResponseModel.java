package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.Action;
import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.TypeFlux;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FluxResponseModel extends Action {
    private String etat;
    private String nom;
    private Date dateTraitement;
    private String motifRejet;
    private Integer nombreLigneTotal;
    private Integer nombreLigneValide;
    private Integer nombreLigneInvalide;
    private TypeFlux typeFlux;
    private Partenaire partenaire;
    private Produit produit;
}
