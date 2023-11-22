package com.soa.vie.takaful.requestmodels;



import java.util.Date;

import com.soa.vie.takaful.entitymodels.Contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAvenantRequestModel {
	
	private Date dateEffet;
	private Contract contrat;

}
