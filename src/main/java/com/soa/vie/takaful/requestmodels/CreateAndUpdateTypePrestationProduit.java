package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.PieceJointe;
import com.soa.vie.takaful.entitymodels.Produit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateTypePrestationProduit {

    private Integer delaiDeclaration;

    private Produit produitId;
    
    private String typePrestationId;
    
	private List<PieceJointe> pieceJointes;


}
