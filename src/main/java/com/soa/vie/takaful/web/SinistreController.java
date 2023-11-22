package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateSinistre;
import com.soa.vie.takaful.requestmodels.UpdateSinistre;
import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.requestmodels.AddSinistreMotif;
import com.soa.vie.takaful.responsemodels.PrestationSinistreModelResponse;
import com.soa.vie.takaful.services.ISinistreService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
@Slf4j
public class SinistreController {

	@Autowired
	private ISinistreService sinistreService;

	@PostMapping("addSinistre")
	public PrestationSinistreModelResponse createPrestationSinitre(@RequestBody CreateSinistre requestModel)
			throws InterruptedException, ExecutionException {
		return sinistreService.createSinistre(requestModel);
	}

	@GetMapping("getSinistre/{contratId}")
	public List<PrestationSinistreModelResponse> getSinitre(@PathVariable String contratId)
			throws InterruptedException, ExecutionException {
		log.info("getSinistre id : " + contratId);
		return sinistreService.getSinistrebyContrat(contratId);
	}

	@PutMapping("updateSinistre/{id}")
	public PrestationSinistreModelResponse updateSinitre(@RequestBody UpdateSinistre model, @PathVariable String id)
			throws InterruptedException, ExecutionException {
		return sinistreService.updateSinistre(model, id);
	}

	@DeleteMapping("Sinistre/delete/{id}")
	public void deleteSinistre(@PathVariable String id) throws InterruptedException, ExecutionException {
		sinistreService.deleteSinistre(id);
	}

	@PostMapping("sinistre/setStatus/{id}/{statut}")
	public Page<PrestationSinistreModelResponse> getDecesContratByStatus(@PathVariable String id,
			@PathVariable String statut,
			@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = "" + Integer.MAX_VALUE) int limit,
			@RequestBody AddSinistreMotif model) throws InterruptedException, ExecutionException {
		return sinistreService.setStatut(id, statut, page, limit, model.getMotif());
	}

	@GetMapping("findSinistre")
	public List<PrestationSinistre> searchSinistre(@RequestParam("searchby") String searchby,
			@RequestParam("searchfor") String searchfor) {
		return sinistreService.searchSinistre(searchby, searchfor);
	}

	@PostMapping("notif-lettre-relance")
	public void notifLettreRelance(@RequestParam("numContrat") String numContrat,
			@RequestParam("pointVente") String pointVente) throws InterruptedException, ExecutionException {
		sinistreService.notifLettreRelance(numContrat, pointVente);
	}

}