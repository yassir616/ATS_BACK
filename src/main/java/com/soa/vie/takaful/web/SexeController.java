package com.soa.vie.takaful.web;

import java.util.ArrayList;
import java.util.List;

import com.soa.vie.takaful.requestmodels.SimpleModelRequest;
import com.soa.vie.takaful.util.SexeEnum;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class SexeController {
 
	@GetMapping("sexe")
	public List<SimpleModelRequest> getSexe() {
        List<SimpleModelRequest> situationList = new ArrayList<>();
        situationList.add(new SimpleModelRequest(SexeEnum.FEMME.getaction(),SexeEnum.FEMME.toString()));
        situationList.add(new SimpleModelRequest(SexeEnum.HOMME.getaction(),SexeEnum.HOMME.toString()));

		return situationList;

	}
}
