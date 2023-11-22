package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.DecesProduit;

import com.soa.vie.takaful.entitymodels.PieceJointe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateDecesProduitCauseRestitution {
	
	private Integer status;

	private DecesProduit idDeces;

	private String idRestitution;
	
	private String idCauseRestitution;
	
	private List<PieceJointe> pieceJointes;

}
