package com.soa.vie.takaful.web;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


import com.soa.vie.takaful.responsemodels.EncaissementResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.entitymodels.Encaissement;
import com.soa.vie.takaful.repositories.ICotisationRepository;
import com.soa.vie.takaful.repositories.IEmissionGlobaleRepository;
import com.soa.vie.takaful.requestmodels.BordereauEncaissementRequestModel;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateEncaissement;
import com.soa.vie.takaful.requestmodels.EncaissementGroupeRequestModel;
import com.soa.vie.takaful.services.ICotisationService;
import com.soa.vie.takaful.services.IEncaissementService;
import com.soa.vie.takaful.util.EtatCotisation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/private")
public class EncaissementController {
	@Autowired
	private IEncaissementService encaissementService;

	@Autowired
	private ICotisationRepository cotisationRepository;

	@Autowired
	private ICotisationService cotisationService;

	@Autowired
	private IEmissionGlobaleRepository emissionGlobaleRepository;

	@GetMapping(path = "encaissement/cotisation/{id}")
	public List<EncaissementResponseModel> encaissemntByCotisation(@PathVariable String id)
			throws InterruptedException, ExecutionException {
		return encaissementService.encaissementByCotisation(id);

	}

	@PostMapping(path = "addEncaissement")
	public EncaissementResponseModel createEncaissement(@RequestBody CreateAndUpdateEncaissement model)
			throws InterruptedException, ExecutionException {
		return encaissementService.createEncaissement(model);
	}

	@GetMapping(path = "encaissement/deleteCotisation/{numeroLot}")
	public void DeleteLot(@PathVariable String numeroLot) throws InterruptedException, ExecutionException {
		List<Cotisation> cotisations = cotisationService.getCotisationsBynumeroLot(numeroLot);
		for (Cotisation requests : cotisations) {
			Optional<Cotisation> cotisation = cotisationRepository.findById(requests.getId());
			log.info("cotisations id : " + cotisation.get().getId());
			cotisation.get().setEtatCotisation(EtatCotisation.EMIS);
			cotisation.get().setNumeroLot(null);
		}
		emissionGlobaleRepository.deleteByNumeroLot(numeroLot);
	}

	@PostMapping(path = "encaissement/EncaissementLot")
	public void EncaissementLot(@RequestBody EncaissementGroupeRequestModel model)
			throws InterruptedException, ExecutionException {
		log.info("numeroLot" + model.getNumeroLot());
		encaissementService.EncaissementLot(model);
	}

	@PostMapping(path="genererBordereauEncaissement")
	public List<Encaissement> genererBordereau(@RequestParam String partenaireId,@RequestParam String dateEncaissement,@RequestParam String compteId)
	throws InterruptedException, ExecutionException {
		 return encaissementService.genererBordereauEncaissement(partenaireId, dateEncaissement, compteId);
	}

	@GetMapping(path = "getBordereaux")
	public List<BordereauEncaissementRequestModel> getAllBordereaux() throws InterruptedException, ExecutionException{
		log.info("getting bordereaux...");
		return encaissementService.getAllBordereaux();
	}
}
