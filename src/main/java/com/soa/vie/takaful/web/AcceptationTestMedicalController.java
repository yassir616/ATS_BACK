package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAcceptationTestMedicalModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationTestMedicalModelResponse;
import com.soa.vie.takaful.services.IAcceptationTestMedicalService;

import org.modelmapper.ModelMapper;
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
public class AcceptationTestMedicalController {

	@Autowired
	private IAcceptationTestMedicalService acceptationTestMedicalService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("acceptation-test-medical")
	public AcceptationTestMedicalModelResponse createAcceptationLaboratoire(
			@RequestBody CreateAcceptationTestMedicalModelRequest model) throws InterruptedException, ExecutionException {
		return acceptationTestMedicalService.createAcceptationTestMedical(model);

	}

	@GetMapping("acceptation-test-medical-laboratoire")
	public AcceptationTestMedicalModelResponse getAcceptationsTestAndLabo(@RequestParam String acceptation,
			@RequestParam String acceptationlabo) throws InterruptedException, ExecutionException {
		return acceptationTestMedicalService.getAcceptationsTestAndLabo(acceptation, acceptationlabo);
	}

	@GetMapping("acceptation-test-medical-examen")
	public AcceptationTestMedicalModelResponse getAcceptationsTestAndExamens(@RequestParam String acceptation,
			@RequestParam String acceptationexamn) throws InterruptedException, ExecutionException {
		return acceptationTestMedicalService.getAcceptationsTestAndExamens(acceptation, acceptationexamn);
	}

	@GetMapping("acceptation-test-medical-examinateur")
	public AcceptationTestMedicalModelResponse getAcceptationsTestAndExaminateur(@RequestParam String acceptation,
			@RequestParam String acceptationexamn) throws InterruptedException, ExecutionException {
		return acceptationTestMedicalService.getAcceptationsTestAndExaminateur(acceptation, acceptationexamn);
	}

	@GetMapping("acceptation-test-medical-conseil")
	public AcceptationTestMedicalModelResponse getAcceptationsTestAndConseil(@RequestParam String acceptation,
			@RequestParam String acceptationconseil) throws InterruptedException, ExecutionException {
		return acceptationTestMedicalService.getAcceptationsTestAndConseil(acceptation, acceptationconseil);
	}

	@PutMapping("acceptation-test-medical")
	public AcceptationTestMedicalModelResponse updateAcceptationsTestAndLabo(
			@RequestBody CreateAcceptationTestMedicalModelRequest model, String id) throws InterruptedException, ExecutionException {

		return modelMapper.map(acceptationTestMedicalService.updateAcceptationTestMedical(model, id),
				AcceptationTestMedicalModelResponse.class);

	}

	@GetMapping("acceptation-test-medical-auxiliaire")
	public List<AcceptationTestMedicalModelResponse> getAcceptationsTestByAuxiliaire(@RequestParam String auxiliaire,
			@RequestParam String typeAuxiliaire, @RequestParam String partenaire) throws InterruptedException, ExecutionException {
		return acceptationTestMedicalService.getAcceptationsTestByAuxiliare(auxiliaire, typeAuxiliaire, partenaire);
	}

}