package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ejb.EJBException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationExaminateurModelResponse;
import com.soa.vie.takaful.services.IAcceptationExaminateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public  class AcceptationExaminateurController {


    @Autowired
	private IAcceptationExaminateurService acceptationExaminateur;
    
	@PostMapping("acceptation-examinateur")
	public AcceptationExaminateurModelResponse createAcceptationExaminateur(@RequestBody CreateallAcceptationTestKindModelRequest model) throws EJBException, InterruptedException, ExecutionException {

		return acceptationExaminateur.createAcceptationExaminateur(model);
	}

	@GetMapping("acceptation-examinateur")
	public List<AcceptationExaminateurModelResponse> getByAcceptation(@RequestParam String acceptation) throws InterruptedException, ExecutionException {
		return acceptationExaminateur.acceptationExaminateurByAcceptation(acceptation);

    }
    
}