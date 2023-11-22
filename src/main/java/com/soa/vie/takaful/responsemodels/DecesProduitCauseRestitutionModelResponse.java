package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
 public class DecesProduitCauseRestitutionModelResponse extends AbstractBaseEntity {

    /**
     *
     */

    private Integer status;

   
    @JsonIgnore
    private DecesProduit deces;
    
    
    private Restitution restitution;

    
    private CauseRestitution causeRestitution;

	
    private List<PieceJointe> pieceJointes = new ArrayList<>();

	


	
	
	 
}