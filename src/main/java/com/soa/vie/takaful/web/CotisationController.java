package com.soa.vie.takaful.web;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.CotisationModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.requestmodels.AnnulationCotisationRequest;
import com.soa.vie.takaful.requestmodels.CotisationRequestDTO;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.requestmodels.CreateEmissionGlobale;
import com.soa.vie.takaful.requestmodels.EmissionGroupeRequestModel;
import com.soa.vie.takaful.requestmodels.ValidationGroupeRequestModel;
import com.soa.vie.takaful.services.ICotisationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/private")
@Slf4j
public class CotisationController {

	@Autowired
	private ICotisationService cotisationService;

	@GetMapping(path = "cotisations")
	public Page<CotisationModelResponse> cotisations(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit)
			throws InterruptedException, ExecutionException {
		return cotisationService.getCotisations(page, limit);

	}

	@GetMapping(path = "cotisation/contrat/{id}")
	public List<CotisationModelResponse> cotisationByContrat(@PathVariable String id)
			throws InterruptedException, ExecutionException {
		return cotisationService.getCotisationByContrat(id);
	}

	@PostMapping(path = "addCotisation")
	public CotisationModelResponse createCotisation(@RequestBody CreateAndUpdateCotisation model)
			throws InterruptedException, ExecutionException {
		return cotisationService.createCotisation(model);
	}

	@PostMapping(path = "annulationCotisation/{id}")
	public CotisationModelResponse AnnulationCotisation(@RequestBody AnnulationCotisationRequest model,
			@PathVariable String id) throws InterruptedException, ExecutionException {
		return cotisationService.AnnulationCotisation(id, model);
	}

	@PostMapping(path = "cotisation/emissionGlobale")
	public List<CotisationRequestDTO> getEmissionGlobale(@RequestBody EmissionGroupeRequestModel requestModel) {
		log.info("Début du Request getEmissionGlobale avec partenaireId={}, produitId={}, startDate={}, endDate={}",
				requestModel.getPartenaireId(), requestModel.getProduitId(), requestModel.getStartDate(), requestModel.getEndDate());
		return cotisationService.getEmissionGlobale(requestModel);
	}

	@PutMapping(path = "cotisation/validationGlobale")
	public CotisationModelResponse validationGlobale(@RequestBody ValidationGroupeRequestModel requestModel)
			throws InterruptedException, ExecutionException {
		List<CotisationModelResponse> listCotisation = requestModel.getListCotisation();
		CreateEmissionGlobale emissionGlobale = requestModel.getCreateEmissionGlobale();
		String reference = requestModel.getReference();
		log.info("controller : " + reference);
		return cotisationService.changeStatusCotisation(listCotisation, emissionGlobale, reference);
	}

	@GetMapping(path = "cotisation/findLot/{numeroLot}")
	public List<Cotisation> findCotisationByNumeroLot(@PathVariable String numeroLot)
			throws InterruptedException, ExecutionException {
		return cotisationService.getCotisationsBynumeroLot(numeroLot);
	}

}
