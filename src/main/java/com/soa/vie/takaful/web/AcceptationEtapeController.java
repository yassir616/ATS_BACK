package com.soa.vie.takaful.web;

import com.soa.vie.takaful.requestmodels.CreateAcceptationEtapeModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationEtapeModelResponse;
import com.soa.vie.takaful.services.IAcceptationEtapeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public  class AcceptationEtapeController {


    @Autowired
	private IAcceptationEtapeService acceptationEtapeService;


	@PostMapping("acceptation-etape")
	public AcceptationEtapeModelResponse createAcceptationEtape(@RequestBody CreateAcceptationEtapeModelRequest model) throws Exception {
	
		return acceptationEtapeService.createAcceptationEtape(model);
    }
}