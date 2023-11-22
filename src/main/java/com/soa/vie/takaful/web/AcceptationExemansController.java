package com.soa.vie.takaful.web;

import java.util.List;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationExamensModelResponse;
import com.soa.vie.takaful.services.IAcceptationExamensService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public  class AcceptationExemansController {

    @Autowired
	private IAcceptationExamensService acceptationExamens;

	@PostMapping("acceptation-examens")
	public AcceptationExamensModelResponse createAcceptationExamens(@RequestBody CreateallAcceptationTestKindModelRequest model) throws Exception {
	
		return acceptationExamens.createAcceptationExamens(model);
	}

	@GetMapping("acceptation-examens")
	public List<AcceptationExamensModelResponse> getByAcceptation(@RequestParam String acceptation) throws Exception {

		return acceptationExamens.acceptationsExamensByAcceptation(acceptation);

    }
    
}