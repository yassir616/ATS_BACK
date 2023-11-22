package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationSpecialisteModelResponse;
import com.soa.vie.takaful.services.IAcceptationSpecialisteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public  class AcceptationSpecialisteController {


    @Autowired
	private IAcceptationSpecialisteService acceptationSpecialiste;

	@PostMapping("acceptation-specialiste")
	public AcceptationSpecialisteModelResponse createAcceptationSpecialiste(@RequestBody CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException {
		return acceptationSpecialiste.createAcceptationSpecialiste(model);

	}

	@GetMapping("acceptation-specialiste")
	public List<AcceptationSpecialisteModelResponse> getByAcceptation(@RequestParam String acceptation) throws InterruptedException, ExecutionException {
		return acceptationSpecialiste.acceptationsSpecialisteByAcceptation(acceptation);
    }

    @PutMapping("acceptation-specialiste")
	public AcceptationSpecialisteModelResponse updateAcceptationSpecialiste(@RequestBody CreateallAcceptationTestKindModelRequest model,String id) throws InterruptedException, ExecutionException {
		return acceptationSpecialiste.updateAcceptationSpecialiste(model,id);
	}
    
}