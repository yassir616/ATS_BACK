package com.soa.vie.takaful.requestmodels;

import java.util.List;


import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Periodicite;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDecesProduit {

		private String code;
		
		private String libelle;
		
		private Float fraisGestion;	    
		
		private Float fraisAcquisition;

		private Float tvaFraisGestion;	 
				
		private Float tvaFraisAcquisition;
		
		private Float taxe;	    
		
		private Float montantAccessoire;	    
		
		private String categorieId;
		
		private String risqueId;	    
			    	    	    
	    private List<Periodicite> periodicites;
	    
	    private List<Exclusion> exclusions;
	    
	   
}
