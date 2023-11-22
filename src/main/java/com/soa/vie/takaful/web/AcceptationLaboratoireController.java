package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationLaboratoireModelResponse;
import com.soa.vie.takaful.services.IAcceptationLaboratoireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public  class AcceptationLaboratoireController {

    @Autowired
	private IAcceptationLaboratoireService acceptationLaboratoire;

	@PostMapping("acceptation-laboratoire")
	public AcceptationLaboratoireModelResponse createAcceptationLaboratoire(@RequestBody CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException {
		return acceptationLaboratoire.createAcceptationLaboratoire(model);
	}

	@GetMapping("acceptation-laboratoire")
	public List<AcceptationLaboratoireModelResponse> getByAcceptation(@RequestParam String acceptation) throws InterruptedException, ExecutionException {
		return acceptationLaboratoire.acceptationsLaboratoireByAcceptation(acceptation);
    }
    
}