package com.soa.vie.takaful.requestmodels;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Honoraire;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateNormeSelection {
    private  Integer ageMax;

	private Integer ageMin;
	
	private Integer capitalMax;
	
    private Integer capitalMin;

    private List<Honoraire> honoraires;
}
