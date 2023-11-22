package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.PieceJointe;
import com.soa.vie.takaful.entitymodels.Produit;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TypePrestationProduitModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private Integer delaiDeclaration;

    @JsonIgnore
    private Produit produit;// IdProduit
   
    private TypePrestationModelResponse typePrestation;// Idtypeprestation

    private List<PieceJointe> pieceJointe = new ArrayList<>();
}