package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateAuxiliaire;
import com.soa.vie.takaful.responsemodels.AuxiliaireModelResponse;
import com.soa.vie.takaful.services.IAuxiliaireService;

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

@RestController
@RequestMapping("api/private")
public class AuxiliaireController {

	
	@Autowired
	private IAuxiliaireService auxiliaireService;


	@PostMapping("auxiliaire/add")
	public AuxiliaireModelResponse createAuxiliaire(@RequestBody CreateAndUpdateAuxiliaire model) throws InterruptedException, ExecutionException {
		return auxiliaireService.createAuxiliaire(model);

	}

	@PutMapping("auxiliaire/{id}")
	public AuxiliaireModelResponse updateAuxiliaire(@PathVariable String id, @RequestBody CreateAndUpdateAuxiliaire model) throws InterruptedException, ExecutionException {
		return auxiliaireService.updateAuxiliaire(id,model);
	}

	@GetMapping("auxiliaire")
	public Page<AuxiliaireModelResponse> getAllAuxiliaires(@RequestParam("page") int page,
			@RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
			@RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
			return auxiliaireService.getAuxiliaires(page, limit, sort, direction);
	}


	@GetMapping("auxiliaire/{id}")
	public AuxiliaireModelResponse getauxiliaireById(@PathVariable String id) throws InterruptedException, ExecutionException {
		return auxiliaireService.getAuxiliaireById(id);
	}

	@GetMapping("auxiliairebytype")
	public List<AuxiliaireModelResponse> getAuxiliairesByType(@RequestParam("type") String type) throws InterruptedException, ExecutionException {
		return auxiliaireService.getAuxiliairebytype(type);
	}

	@GetMapping("auxiliairebystatut")
	public List<Auxiliaire> getAllAuxiliairesByStatut() throws InterruptedException, ExecutionException {
			return auxiliaireService.getAuxiliairebystatut();
	}
}



