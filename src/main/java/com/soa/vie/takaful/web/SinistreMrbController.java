package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.PrestationSinistreMrbModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soa.vie.takaful.entitymodels.SinistreMrb;
import com.soa.vie.takaful.requestmodels.CreateSinistre;
import com.soa.vie.takaful.requestmodels.UpdateSinistreMrb;
import com.soa.vie.takaful.services.ISinistreMrbService;

@RestController
@RequestMapping("api/private")
public class SinistreMrbController {

	@Autowired
	private ISinistreMrbService sinistreService;

	@PostMapping("addSinistreMrb")
	public PrestationSinistreMrbModelResponse createPrestationSinitre(@RequestBody CreateSinistre requestModel)
			throws InterruptedException, ExecutionException {
		return sinistreService.createSinistre(requestModel);
	}

	@GetMapping("getSinistreMrb/{contratId}")
	public List<PrestationSinistreMrbModelResponse> getSinitre(@PathVariable String contratId)
			throws InterruptedException, ExecutionException {
		return sinistreService.getSinistrebyContrat(contratId);
	}

	@PutMapping("updateSinistreMrb/{id}")
	public PrestationSinistreMrbModelResponse updateSinitre(@RequestBody UpdateSinistreMrb model,
			@PathVariable String id) throws InterruptedException, ExecutionException {
		return sinistreService.updateSinistre(model, id);
	}

	@PostMapping("/recupereDataSinistreMrb")
	public SinistreMrb saveSinistreMrbData(@RequestBody SinistreMrb sinistreMrb)
			throws InterruptedException, ExecutionException {
		return sinistreService.getDataFromSinistre(sinistreMrb);
	}
}
