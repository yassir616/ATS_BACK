package com.soa.vie.takaful.web;

import java.util.List;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationConseilModelResponse;
import com.soa.vie.takaful.services.IAcceptationConseilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class AcceptationConseilController {

	@Autowired
	private IAcceptationConseilService acceptationConseil;

	@PostMapping("acceptation-conseil")
	public AcceptationConseilModelResponse createAcceptationConseil(@RequestBody CreateallAcceptationTestKindModelRequest model) throws Exception {
		
		return acceptationConseil.createAcceptationConseil(model);
	}

	@GetMapping("acceptation-conseil")

	public List<AcceptationConseilModelResponse> getByAcceptation(@RequestParam String acceptation) throws Exception {
	
		return acceptationConseil.acceptationConseilByAcceptation(acceptation);

	}


}