package com.soa.vie.takaful.entitymodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Flux extends Action {

    @Column
    private String etat;

    @Column
    private String nom;

    @Column(columnDefinition = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateTraitement;

    @Column
    private String motifRejet;

    @Column
    private Integer nombreLigneTotal;

    @Column
    private Integer nombreLigneValide;

    @Column
    private Integer nombreLigneInvalide;

    @ManyToOne
    private TypeFlux typeFlux;

    @ManyToOne
    private Partenaire partenaire;

    @ManyToOne
    private Produit produit;
}
