package com.soa.vie.takaful.web;

import java.util.ArrayList;
import java.util.List;

import com.soa.vie.takaful.requestmodels.SimpleModelRequest;
import com.soa.vie.takaful.util.SituationFamilialeEnum;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class SituationFamilialeController {
 

	@GetMapping("situation")
	public List<SimpleModelRequest> getSituations() {
        List<SimpleModelRequest> situationList = new ArrayList<>();
        situationList.add(new SimpleModelRequest(SituationFamilialeEnum.CELIBATAIRE.getaction(),SituationFamilialeEnum.CELIBATAIRE.toString()));
        situationList.add(new SimpleModelRequest(SituationFamilialeEnum.MARIE.getaction(),SituationFamilialeEnum.MARIE.toString()));

		return situationList;

	}

}
